package com.slut.simrec.pswd.category.detail.m;

import android.text.TextUtils;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.database.pswd.dao.PassCatDao;
import com.slut.simrec.database.pswd.dao.PassDao;
import com.slut.simrec.pswd.category.CategoryConst;
import com.slut.simrec.pswd.category.detail.v.CatDeleteType;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/9.
 */

public class CatDetailModelImpl implements CatDetailModel {

    @Override
    public void loadData(PassCat passCat, OnDataLoadListener onDataLoadListener) {
        if (passCat == null) {
            onDataLoadListener.onLoadError(ResUtils.getString(R.string.error_query_failed));
            return;
        }
        String catuuid = passCat.getUuid();
        List<Password> passwordList = null;
        try {
            passwordList = PassDao.getInstances().queryByCatUUID(catuuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (passwordList == null) {
            onDataLoadListener.onLoadError(ResUtils.getString(R.string.error_query_failed));
        } else {
            onDataLoadListener.onLoadSuccess(0, passwordList);
        }
    }

    @Override
    public void delete(int deleteType, PassCat passCat, OnDeleteListener onDeleteListener) {
        if (passCat == null) {
            onDeleteListener.onDeleteError(ResUtils.getString(R.string.error_delete_cat_null));
            return;
        }
        if (passCat.getUuid().equals(CategoryConst.UUID_UNSPECIFIC)) {
            onDeleteListener.onDeleteError(ResUtils.getString(R.string.error_delete_cat_default));
            return;
        }
        try {
            List<Password> passwordList = PassDao.getInstances().queryByCatUUID(passCat.getUuid());
            PassCatDao.getInstances().deleteByUUID(passCat.getUuid());
            if (deleteType == CatDeleteType.DELETE_CAT_ONLY) {
                for (Password password : passwordList) {
                    PassDao.getInstances().updateCat(password.getUuid(), CategoryConst.UUID_UNSPECIFIC);
                }
            } else {
                PassDao.getInstances().deleteByCatUUID(passCat.getUuid());
            }
            onDeleteListener.onDeleteSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onDeleteListener.onDeleteError(e.getLocalizedMessage());
            } else {
                onDeleteListener.onDeleteError(ResUtils.getString(R.string.error_unknown_exception_happen));
            }
        }
    }

    @Override
    public void edit(PassCat passCat, String title, String url, String iconURL, OnEditListener onEditListener) {
        if (passCat == null) {
            onEditListener.onEditError(ResUtils.getString(R.string.error_edit_null_cat));
            return;
        }
        if (TextUtils.isEmpty(title)) {
            onEditListener.onEditError(ResUtils.getString(R.string.error_category_options_empty_title));
            return;
        }
        String uuid = passCat.getUuid();
        String newTitle = RSAUtils.encrypt(title);
        String newURL = RSAUtils.encrypt(url);
        String newIconURL = RSAUtils.encrypt(iconURL);
        boolean isExpand = passCat.isExpand();
        try {
            PassCatDao.getInstances().update(uuid, newTitle, newURL, newIconURL);
            PassCat newCat = new PassCat(uuid, newTitle, newURL, newIconURL, isExpand, passCat.getCreateStamp(), System.currentTimeMillis());
            onEditListener.onEditSuccess(newCat);
        } catch (Exception e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onEditListener.onEditError(e.getLocalizedMessage());
            } else {
                onEditListener.onEditError(ResUtils.getString(R.string.error_unknown_exception_happen));
            }
        }
    }

}

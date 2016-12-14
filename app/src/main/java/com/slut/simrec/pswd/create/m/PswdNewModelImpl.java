package com.slut.simrec.pswd.create.m;

import android.text.TextUtils;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.database.pswd.dao.PassCatDao;
import com.slut.simrec.database.pswd.dao.PassDao;
import com.slut.simrec.pswd.category.CategoryConst;
import com.slut.simrec.pswd.category.defaultcat.bean.DefaultCatBean;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;

import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class PswdNewModelImpl implements PswdNewModel {
    @Override
    public void onBackClick(String title, String account, String password, String website, String remark, PassCat passCat, String originPassUUID, OnBackOnClickListener onBackOnClickListener) {
        if (passCat == null) {
            if (TextUtils.isEmpty(title)
                    && TextUtils.isEmpty(account)
                    && TextUtils.isEmpty(password)
                    && TextUtils.isEmpty(website)
                    && TextUtils.isEmpty(remark)
                    && TextUtils.equals(originPassUUID, CategoryConst.UUID_UNSPECIFIC)) {
                onBackOnClickListener.onUINotChange();
            } else {
                onBackOnClickListener.onUIChange();
            }
        } else {
            if (TextUtils.equals(title, passCat.getCatTitle())
                    && TextUtils.isEmpty(account)
                    && TextUtils.isEmpty(password)
                    && TextUtils.isEmpty(remark)
                    && TextUtils.equals(website, passCat.getCatUrl())
                    && TextUtils.equals(originPassUUID.trim(), passCat.getUuid())) {
                onBackOnClickListener.onUINotChange();
            } else {
                onBackOnClickListener.onUIChange();
            }
        }
    }

    @Override
    public void save(String title, String account, String password, String website, String remark, String passCatUUID, OnSavePswdListener onSavePswdListener) {
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(account)) {
            onSavePswdListener.onPswdSaveError(ResUtils.getString(R.string.error_pswd_new_titleaccount_null));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            onSavePswdListener.onPswdSaveError(ResUtils.getString(R.string.error_pswd_new_password_null));
            return;
        }
        String newTitle = title;
        String newAccount = account;
        String newPassword = RSAUtils.encrypt(password);
        String newWebsite = website;
        String newRemark = remark;

        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();
        Password pwd = new Password(uuid, newTitle, newAccount, newPassword, newWebsite, newRemark, passCatUUID, stamp, stamp);
        try {
            PassDao.getInstances().insertSingle(pwd);
            PassCatDao.getInstances().updateTime(passCatUUID);
            onSavePswdListener.onPswdSaveSuccess(pwd);
        } catch (Exception e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onSavePswdListener.onPswdSaveError(e.getLocalizedMessage());
            } else {
                onSavePswdListener.onPswdSaveError(ResUtils.getString(R.string.error_exception_happened));
            }
        }
    }
}

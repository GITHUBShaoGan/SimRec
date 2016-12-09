package com.slut.simrec.main.fragment.pswd.m;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.database.pswd.dao.PassCatDao;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.database.pswd.dao.PassDao;
import com.slut.simrec.pswd.category.CategoryConst;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public class PswdModelImpl implements PswdModel {

    @Override
    public void loadPassCat(long pageNo, long pageSize, OnPassCatLoadListener onPassCatLoadListener) {
        if (pageNo < 1 || pageSize < 1) {
            onPassCatLoadListener.onPassCatLoadError(ResUtils.getString(R.string.error_size));
            return;
        }
        List<PassCat> passCatList = null;
        try {
            passCatList = PassCatDao.getInstances().queryByPage(pageNo, pageSize);
        } catch (Exception e) {

        }
        if (passCatList == null) {
            onPassCatLoadListener.onPassCatLoadError(ResUtils.getString(R.string.error_query_failed));
        } else {
            if (pageNo == 1) {
                PassConfig passConfig = null;
                try {
                    passConfig = PassConfigDao.getInstances().querySingleConfig();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (passConfig != null) {
                    PassCat unspecificCat = new PassCat(CategoryConst.UUID_UNSPECIFIC, RSAUtils.encrypt(CategoryConst.TITLE_UNSPECIFIC), RSAUtils.encrypt(CategoryConst.URL_UNSPECIFIC), RSAUtils.decrypt(CategoryConst.ICONURL_UNSPECIFIC), System.currentTimeMillis(), System.currentTimeMillis());
                    passCatList.add(0, unspecificCat);
                }
            }
            List<List<Password>> listList = new ArrayList<>();
            for (PassCat passCat : passCatList) {
                List<Password> passwordList = null;
                try {
                    passwordList = PassDao.getInstances().queryByCatUUID(passCat.getUuid());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (passwordList == null) {
                    listList.add(new ArrayList<Password>());
                } else {
                    listList.add(passwordList);
                }
            }
            if (passCatList.size() < pageSize) {
                onPassCatLoadListener.onPassCatLoadSuccess(DataLoadType.END, passCatList, listList);
            } else {
                onPassCatLoadListener.onPassCatLoadSuccess(DataLoadType.FINISH, passCatList, listList);
            }
        }
    }
}

package com.slut.simrec.pswd.detail.m;

import android.text.TextUtils;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.database.pswd.dao.PassDao;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;

/**
 * Created by 七月在线科技 on 2016/12/13.
 */

public class PassDetailModelImpl implements PassDetailModel {

    @Override
    public void update(String title, String account, String password, String websiteURL, String remark, Password pswd, PassCat passCat, OnUpdateListener onUpdateListener) {
        if (password == null || passCat == null) {
            onUpdateListener.onUpdateError("");
            return;
        }
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(account)) {
            onUpdateListener.onUpdateError("");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            onUpdateListener.onUpdateError("");
            return;
        }
        String newTitle = title;
        String newAccount = account;
        String newPassword = RSAUtils.encrypt(password);
        String newWebsite = websiteURL;
        String newRemark = remark;
        long stamp = System.currentTimeMillis();

        String passUUID = pswd.getUuid();
        String catUUID = passCat.getUuid();

        Password p = new Password(passUUID, newTitle, newAccount, newPassword, newWebsite, newRemark, catUUID, pswd.getCreateStamp(), stamp);
        try {
            PassDao.getInstances().update(p);
            onUpdateListener.onUpdateSuccess(p);
        } catch (Exception e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onUpdateListener.onUpdateError(e.getLocalizedMessage());
            } else {
                onUpdateListener.onUpdateError(ResUtils.getString(R.string.error_unknown_exception_happen));
            }
        }
    }

}

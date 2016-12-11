package com.slut.simrec.pswd.master.text.m;

import android.text.TextUtils;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;

import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/11.
 */

public class TextPassModelImpl implements TextPassModel {

    @Override
    public void createPass(String password, OnCreatePassListener onCreatePassListener) {
        if (TextUtils.isEmpty(password) || password.length() != 6) {
            onCreatePassListener.onCreateError(ResUtils.getString(R.string.error_pass_length));
            return;
        }
        PassConfig passConfig = null;
        try {
            passConfig = PassConfigDao.getInstances().querySingleConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String uuid = UUID.randomUUID().toString();
        String textPass = RSAUtils.encrypt(password);
        String empty = "";
        long stamp = System.currentTimeMillis();
        if (passConfig == null) {
            //以前没有主密码配置
            PassConfig config = new PassConfig(uuid, empty, empty, textPass,false, PassConfig.LockType.TEXT, stamp, stamp);
            try {
                PassConfigDao.getInstances().insertSingle(config);
                onCreatePassListener.onCreateSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                    onCreatePassListener.onCreateError(e.getLocalizedMessage());
                } else {
                    onCreatePassListener.onCreateError(ResUtils.getString(R.string.error_unknown_exception_happen));
                }
            }
        } else {
            //以前有主密码配置
            passConfig.setTextPass(textPass);
            passConfig.setUpdateStamp(stamp);
            passConfig.setPreferLockType(PassConfig.LockType.TEXT);
            try {
                PassConfigDao.getInstances().updateSingle(passConfig);
                onCreatePassListener.onCreateSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                    onCreatePassListener.onCreateError(e.getLocalizedMessage());
                } else {
                    onCreatePassListener.onCreateError(ResUtils.getString(R.string.error_unknown_exception_happen));
                }
            }
        }
    }

}

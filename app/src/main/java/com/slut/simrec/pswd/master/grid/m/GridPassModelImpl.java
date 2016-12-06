package com.slut.simrec.pswd.master.grid.m;

import android.text.TextUtils;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;

import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class GridPassModelImpl implements GridPassModel {

    @Override
    public void createPass(String password, OnCreatePassCallback onCreatePassCallback) {
        if (TextUtils.isEmpty(password) || password.length() != 6) {
            onCreatePassCallback.onCreatePassError(ResUtils.getString(R.string.error_pass_length));
            return;
        }
        PassConfig passConfig = null;
        try {
            passConfig = PassConfigDao.getInstances().querySingleConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String uuid = UUID.randomUUID().toString();
        String gridPass = RSAUtils.encrypt(password);
        String empty = "";
        long stamp = System.currentTimeMillis();
        if (passConfig == null) {
            //以前没有主密码配置
            PassConfig config = new PassConfig(uuid, gridPass, empty, empty,false, PassConfig.LockType.GRID, stamp, stamp);
            try {
                PassConfigDao.getInstances().insertSingle(config);
                onCreatePassCallback.onCreatePassSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                    onCreatePassCallback.onCreatePassError(e.getLocalizedMessage());
                } else {
                    onCreatePassCallback.onCreatePassError(ResUtils.getString(R.string.error_unknown_exception_happen));
                }
            }
        } else {
            //以前有主密码配置
            passConfig.setGridPass(gridPass);
            passConfig.setUpdateStamp(stamp);
            passConfig.setPreferLockType(PassConfig.LockType.GRID);
            try {
                PassConfigDao.getInstances().updateSingle(passConfig);
                onCreatePassCallback.onCreatePassSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                    onCreatePassCallback.onCreatePassError(e.getLocalizedMessage());
                } else {
                    onCreatePassCallback.onCreatePassError(ResUtils.getString(R.string.error_unknown_exception_happen));
                }
            }
        }
    }

}

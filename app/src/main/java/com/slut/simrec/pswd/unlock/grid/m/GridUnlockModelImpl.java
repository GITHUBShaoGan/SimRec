package com.slut.simrec.pswd.unlock.grid.m;

import android.text.TextUtils;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class GridUnlockModelImpl implements GridUnlockModel {

    @Override
    public void unlock(String password, OnUnLockListener onUnLockListener) {
        if (TextUtils.isEmpty(password) || password.length() != 6) {
            onUnLockListener.onUnlockFailed();
            return;
        }
        PassConfig passConfig = null;
        try {
            passConfig = PassConfigDao.getInstances().querySingleConfig();
        } catch (Exception e) {

        }
        if (passConfig == null) {
            onUnLockListener.onUnlockError(ResUtils.getString(R.string.error_exception_happened));
            return;
        } else {
            String myPass = RSAUtils.decrypt(passConfig.getGridPass());
            if (myPass.equals(password)) {
                onUnLockListener.onUnlockSuccess();
            } else {
                onUnLockListener.onUnlockFailed();
            }
        }
    }

}

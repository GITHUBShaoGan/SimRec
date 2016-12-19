package com.slut.simrec.pswd.master.pattern.m;

import android.text.TextUtils;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;

import java.util.UUID;

import static android.R.attr.password;

/**
 * Created by 七月在线科技 on 2016/12/16.
 */

public class PatternPassModelImpl implements PatternPassModel {

    @Override
    public void setPattern(String password, OnPatternSetListener onPatternSetListener) {
        PassConfig passConfig = null;
        try {
            passConfig = PassConfigDao.getInstances().querySingleConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String uuid = UUID.randomUUID().toString();
        String patternPass = password;
        String empty = "";
        long stamp = System.currentTimeMillis();
        if (passConfig == null) {
            //以前没有主密码配置
            PassConfig config = new PassConfig(uuid, empty, patternPass, empty, false, PassConfig.LockType.PATTERN, stamp, stamp);
            try {
                PassConfigDao.getInstances().insertSingle(config);
                onPatternSetListener.onPatternSetSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                    onPatternSetListener.onPatternSetError(e.getLocalizedMessage());
                } else {
                    onPatternSetListener.onPatternSetError(ResUtils.getString(R.string.error_unknown_exception_happen));
                }
            }
        } else {
            //以前有主密码配置
            passConfig.setPatternPass(patternPass);
            passConfig.setUpdateStamp(stamp);
            passConfig.setPreferLockType(PassConfig.LockType.PATTERN);
            try {
                PassConfigDao.getInstances().updateSingle(passConfig);
                onPatternSetListener.onPatternSetSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                    onPatternSetListener.onPatternSetError(e.getLocalizedMessage());
                } else {
                    onPatternSetListener.onPatternSetError(ResUtils.getString(R.string.error_unknown_exception_happen));
                }
            }
        }
    }

}

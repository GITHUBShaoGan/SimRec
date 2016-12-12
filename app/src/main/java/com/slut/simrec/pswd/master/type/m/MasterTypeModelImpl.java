package com.slut.simrec.pswd.master.type.m;

import android.text.TextUtils;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.utils.ResUtils;

import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/12.
 */

public class MasterTypeModelImpl implements MasterTypeModel {

    @Override
    public void setPass(OnSetPassListener onSetPassListener) {
        PassConfig passConfig = null;
        try {
            passConfig = PassConfigDao.getInstances().querySingleConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long stamp = System.currentTimeMillis();
        if (passConfig == null) {
            //不存在密码
            String uuid = UUID.randomUUID().toString();
            String empty = "";
            passConfig = new PassConfig(uuid, empty, empty, empty, true, PassConfig.LockType.NOT_SET, stamp, stamp);
            try {
                PassConfigDao.getInstances().insertSingle(passConfig);
                onSetPassListener.onSetPassSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                    onSetPassListener.onSetPassError(e.getLocalizedMessage());
                } else {
                    onSetPassListener.onSetPassError(ResUtils.getString(R.string.error_unknown_exception_happen));
                }
            }
        } else {
            passConfig.setFingerPrintAgreed(true);
            passConfig.setCreateStamp(stamp);
            passConfig.setUpdateStamp(stamp);
            try {
                PassConfigDao.getInstances().updateSingle(passConfig);
                onSetPassListener.onSetPassSuccess();
            } catch (Exception e) {
                if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                    onSetPassListener.onSetPassError(e.getLocalizedMessage());
                } else {
                    onSetPassListener.onSetPassError(ResUtils.getString(R.string.error_unknown_exception_happen));
                }
            }
        }
    }

}

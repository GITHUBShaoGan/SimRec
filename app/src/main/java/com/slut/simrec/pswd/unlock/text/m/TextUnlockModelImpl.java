package com.slut.simrec.pswd.unlock.text.m;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;

/**
 * Created by 七月在线科技 on 2016/12/11.
 */

public class TextUnlockModelImpl implements TextUnlockModel {

    @Override
    public void validate(String password, OnValidateListener onValidateListener) {
        if (password == null || password.length() < 4 || password.length() > 128) {
            onValidateListener.onValidateError(ResUtils.getString(R.string.error_text_unlock_length));
            return;
        }
        PassConfig passConfig = null;
        try {
            passConfig = PassConfigDao.getInstances().querySingleConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (passConfig == null) {
            onValidateListener.onValidateError(ResUtils.getString(R.string.error_query_failed));
            return;
        } else {
            String textPass = RSAUtils.decrypt(passConfig.getTextPass());
            if (password.equals(textPass)) {
                onValidateListener.onValidateSuccess();
            } else {
                onValidateListener.onValidateFailed();
            }
        }
    }
}

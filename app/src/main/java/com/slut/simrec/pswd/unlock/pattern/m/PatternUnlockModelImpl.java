package com.slut.simrec.pswd.unlock.pattern.m;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.utils.ResUtils;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by 七月在线科技 on 2016/12/19.
 */

public class PatternUnlockModelImpl implements PatternUnlockModel {
    @Override
    public void validate(List<PatternView.Cell> patternList, OnValidateListener onValidateListener) {
        PassConfig passConfig = null;
        try {
            passConfig = PassConfigDao.getInstances().querySingleConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (passConfig != null) {
            if (!passConfig.isFingerPrintAgreed()) {
                if (passConfig.getPreferLockType() == PassConfig.LockType.PATTERN) {
                    String dataPattern = passConfig.getPatternPass();
                    String checkPattern = PatternUtils.patternToSha1String(patternList);
                    if (dataPattern.equals(checkPattern)) {
                        onValidateListener.onValidateSuccess();
                        return;
                    }
                }
            }
        }
        onValidateListener.onValidateError(ResUtils.getString(R.string.tips_pattern_unlock_error));
    }
}

package com.slut.simrec.pswd.unlock.pattern.m;

import java.util.List;

import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by 七月在线科技 on 2016/12/19.
 */

public interface PatternUnlockModel {

    interface OnValidateListener {

        void onValidateSuccess();

        void onValidateError(String msg);

    }

    void validate(List<PatternView.Cell> patternList, OnValidateListener onValidateListener);

}

package com.slut.simrec.pswd.master.pattern.m;

import com.slut.simrec.database.pswd.bean.Password;

/**
 * Created by 七月在线科技 on 2016/12/16.
 */

public interface PatternPassModel {

    interface OnPatternSetListener {

        void onPatternSetSuccess();

        void onPatternSetError(String msg);

    }

    void setPattern(String password, OnPatternSetListener onPatternSetListener);

}

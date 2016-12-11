package com.slut.simrec.pswd.unlock.text.m;

/**
 * Created by 七月在线科技 on 2016/12/11.
 */

public interface TextUnlockModel {

    interface OnValidateListener {

        void onValidateSuccess();

        void onValidateFailed();

        void onValidateError(String msg);

    }

    void validate(String password, OnValidateListener onValidateListener);

}

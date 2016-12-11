package com.slut.simrec.pswd.master.text.m;

/**
 * Created by 七月在线科技 on 2016/12/11.
 */

public interface TextPassModel {

    interface OnCreatePassListener{

        void onCreateSuccess();

        void onCreateError(String msg);

    }

    void createPass(String password,OnCreatePassListener onCreatePassListener);

}

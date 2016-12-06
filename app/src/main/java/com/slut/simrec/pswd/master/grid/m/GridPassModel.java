package com.slut.simrec.pswd.master.grid.m;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public interface GridPassModel {

    interface OnCreatePassCallback {

        void onCreatePassSuccess();

        void onCreatePassError(String msg);

    }

    void createPass(String password, OnCreatePassCallback onCreatePassCallback);
}

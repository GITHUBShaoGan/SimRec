package com.slut.simrec.pswd.master.type.m;

/**
 * Created by 七月在线科技 on 2016/12/12.
 */

public interface MasterTypeModel {

    interface OnSetPassListener{

        void onSetPassSuccess();

        void onSetPassError(String msg);

    }

    void setPass(OnSetPassListener onSetPassListener);

}

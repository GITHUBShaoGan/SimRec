package com.slut.simrec.main.m;

import com.slut.simrec.database.pswd.bean.PassConfig;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public interface MainModel {

    interface OnUIClickListener {

        void onPswdFuncLock(PassConfig passConfig);

        void onPswdFuncUnlock(PassConfig passConfig);

        void onMasterNotSetBefore();

        void onDataTamper();

        void onPswdClickError(String msg);

    }

    void onUIClick(OnUIClickListener onUIClickListener);
}

package com.slut.simrec.main.m;

import com.slut.simrec.database.pswd.bean.PassConfig;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public interface MainModel {

    interface OnUIClickListener {

        void onPswdFuncLock(int clickType,PassConfig passConfig);

        void onPswdFuncUnlock(int clickType,PassConfig passConfig);

        void onMasterNotSetBefore(int clickType);

        void onDataTamper();

        void onClickError(String msg);

    }

    void onUIClick(int clickType,OnUIClickListener onUIClickListener);
}

package com.slut.simrec.pswd.unlock.grid.m;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public interface GridUnlockModel {

    interface OnUnLockListener {

        void onUnlockSuccess();

        void onUnlockFailed();

        void onUnlockError(String msg);

    }

    void unlock(String password, OnUnLockListener onUnLockListener);

}

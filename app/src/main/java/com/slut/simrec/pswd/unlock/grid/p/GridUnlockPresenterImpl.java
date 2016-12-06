package com.slut.simrec.pswd.unlock.grid.p;

import com.slut.simrec.pswd.unlock.grid.m.GridUnlockModel;
import com.slut.simrec.pswd.unlock.grid.m.GridUnlockModelImpl;
import com.slut.simrec.pswd.unlock.grid.v.GridUnlockView;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class GridUnlockPresenterImpl implements GridUnlockPresenter, GridUnlockModel.OnUnLockListener {

    private GridUnlockModel gridUnlockModel;
    private GridUnlockView gridUnlockView;

    public GridUnlockPresenterImpl(GridUnlockView gridUnlockView) {
        this.gridUnlockView = gridUnlockView;
        this.gridUnlockModel = new GridUnlockModelImpl();
    }

    @Override
    public void onUnlockSuccess() {
        gridUnlockView.onUnlockSuccess();
    }

    @Override
    public void onUnlockFailed() {
        gridUnlockView.onUnlockFailed();
    }

    @Override
    public void onUnlockError(String msg) {
        gridUnlockView.onUnlockError(msg);
    }

    @Override
    public void unlock(String password) {
        gridUnlockModel.unlock(password, this);
    }
}

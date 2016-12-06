package com.slut.simrec.pswd.master.grid.p;

import com.slut.simrec.pswd.master.grid.m.GridPassModel;
import com.slut.simrec.pswd.master.grid.m.GridPassModelImpl;
import com.slut.simrec.pswd.master.grid.v.GridPassView;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class GridPassPresenterImpl implements GridPassPresenter, GridPassModel.OnCreatePassCallback {

    private GridPassModel gridPassModel;
    private GridPassView gridPassView;

    public GridPassPresenterImpl(GridPassView gridPassView) {
        this.gridPassView = gridPassView;
        this.gridPassModel = new GridPassModelImpl();
    }

    @Override
    public void onCreatePassSuccess() {
        gridPassView.onCreatePassSuccess();
    }

    @Override
    public void onCreatePassError(String msg) {
        gridPassView.onCreatePassError(msg);
    }

    @Override
    public void createPass(String password) {
        gridPassModel.createPass(password, this);
    }
}

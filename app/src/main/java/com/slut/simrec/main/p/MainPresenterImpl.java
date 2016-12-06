package com.slut.simrec.main.p;

import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.main.m.MainModel;
import com.slut.simrec.main.m.MainModelImpl;
import com.slut.simrec.main.v.MainView;

import static com.slut.simrec.R.menu.main;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class MainPresenterImpl implements MainPresenter, MainModel.OnFabPswdClickCallback {

    private MainModel mainModel;
    private MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        this.mainModel = new MainModelImpl();
    }

    @Override
    public void onPswdFuncLock(PassConfig passConfig) {
        mainView.onPswdFuncLock(passConfig);
    }

    @Override
    public void onPswdFuncUnlock(PassConfig passConfig) {
        mainView.onPswdFuncUnlock(passConfig);
    }

    @Override
    public void onMasterNotSetBefore() {
        mainView.onMasterNotSetBefore();
    }

    @Override
    public void onDataTamper() {
        mainView.onDataTamper();
    }

    @Override
    public void onPswdClickError(String msg) {
        mainView.onPswdClickError(msg);
    }

    @Override
    public void onFabPswdClick() {
        mainModel.onFabPswdClick(this);
    }
}

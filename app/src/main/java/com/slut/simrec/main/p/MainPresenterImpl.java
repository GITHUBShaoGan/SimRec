package com.slut.simrec.main.p;

import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.main.m.MainModel;
import com.slut.simrec.main.m.MainModelImpl;
import com.slut.simrec.main.v.MainView;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class MainPresenterImpl implements MainPresenter, MainModel.OnUIClickListener {

    private MainModel mainModel;
    private MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        this.mainModel = new MainModelImpl();
    }

    @Override
    public void onPswdFuncLock(int clickType,PassConfig passConfig) {
        mainView.onPswdFuncLock(clickType,passConfig);
    }

    @Override
    public void onPswdFuncUnlock(int clickType,PassConfig passConfig) {
        mainView.onPswdFuncUnlock(clickType,passConfig);
    }

    @Override
    public void onMasterNotSetBefore(int clickType) {
        mainView.onMasterNotSetBefore(clickType);
    }

    @Override
    public void onDataTamper() {
        mainView.onDataTamper();
    }

    @Override
    public void onClickError(String msg) {
        mainView.onPswdClickError(msg);
    }

    @Override
    public void onUIClick(int clickType) {
        mainModel.onUIClick(clickType,this);
    }

}

package com.slut.simrec.pswd.create.p;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.pswd.create.m.PswdNewModel;
import com.slut.simrec.pswd.create.m.PswdNewModelImpl;
import com.slut.simrec.pswd.create.v.PswdNewView;
import com.slut.simrec.pswd.category.defaultcat.bean.DefaultCatBean;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class PswdNewPresenterImpl implements PswdNewPresenter, PswdNewModel.OnBackOnClickListener, PswdNewModel.OnSavePswdListener {

    private PswdNewModel pswdNewModel;
    private PswdNewView pswdNewView;

    public PswdNewPresenterImpl(PswdNewView pswdNewView) {
        this.pswdNewView = pswdNewView;
        this.pswdNewModel = new PswdNewModelImpl();
    }

    @Override
    public void onUIChange() {
        pswdNewView.onUIChange();
    }

    @Override
    public void onUINotChange() {
        pswdNewView.onUINotChange();
    }

    @Override
    public void onBackClick(String title, String account, String password, String website, String remark, PassCat passCat, String originPassUUID) {
        pswdNewModel.onBackClick(title, account, password, website, remark, passCat, originPassUUID, this);
    }

    @Override
    public void save(String title, String account, String password, String website, String remark, String passCatUUID) {
        pswdNewModel.save(title, account, password, website, remark, passCatUUID, this);
    }

    @Override
    public void onPswdSaveSuccess(Password password) {
        pswdNewView.onPswdSaveSuccess(password);
    }

    @Override
    public void onPswdSaveError(String msg) {
        pswdNewView.onPswdSaveError(msg);
    }
}

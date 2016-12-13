package com.slut.simrec.pswd.detail.p;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.pswd.detail.m.PassDetailModel;
import com.slut.simrec.pswd.detail.m.PassDetailModelImpl;
import com.slut.simrec.pswd.detail.v.PassDetailView;

/**
 * Created by 七月在线科技 on 2016/12/13.
 */

public class PassDetailPresenterImpl implements PassDetailPresenter, PassDetailModel.OnUpdateListener {

    private PassDetailModel passDetailModel;
    private PassDetailView passDetailView;

    public PassDetailPresenterImpl(PassDetailView passDetailView) {
        this.passDetailView = passDetailView;
        this.passDetailModel = new PassDetailModelImpl();
    }

    @Override
    public void onUpdateSuccess(Password password) {
        passDetailView.onUpdateSuccess(password);
    }

    @Override
    public void onUpdateError(String msg) {
        passDetailView.onUpdateError(msg);
    }

    @Override
    public void update(String title, String account, String password, String websiteURL, String remark, Password pswd, PassCat passCat) {
        passDetailModel.update(title, account, password, websiteURL, remark, pswd, passCat, this);
    }
}

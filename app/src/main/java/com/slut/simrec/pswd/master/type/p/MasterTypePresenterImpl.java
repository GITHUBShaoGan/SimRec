package com.slut.simrec.pswd.master.type.p;

import com.slut.simrec.pswd.master.type.m.MasterTypeModel;
import com.slut.simrec.pswd.master.type.m.MasterTypeModelImpl;
import com.slut.simrec.pswd.master.type.v.MasterTypeView;

/**
 * Created by 七月在线科技 on 2016/12/12.
 */

public class MasterTypePresenterImpl implements MasterTypePresenter,MasterTypeModel.OnSetPassListener {

    private MasterTypeModel masterTypeModel;
    private MasterTypeView masterTypeView;

    public MasterTypePresenterImpl(MasterTypeView masterTypeView) {
        this.masterTypeView = masterTypeView;
        this.masterTypeModel = new MasterTypeModelImpl();
    }

    @Override
    public void onSetPassSuccess() {
        masterTypeView.onSetPassSuccess();
    }

    @Override
    public void onSetPassError(String msg) {
        masterTypeView.onSetPassError(msg);
    }

    @Override
    public void setPass() {
        masterTypeModel.setPass(this);
    }
}

package com.slut.simrec.pswd.master.text.p;

import com.slut.simrec.pswd.master.text.m.TextPassModel;
import com.slut.simrec.pswd.master.text.m.TextPassModelImpl;
import com.slut.simrec.pswd.master.text.v.TextPassView;

/**
 * Created by 七月在线科技 on 2016/12/11.
 */

public class TextPassPresenterImpl implements TextPassPresenter, TextPassModel.OnCreatePassListener {

    private TextPassModel textPassModel;
    private TextPassView textPassView;

    public TextPassPresenterImpl(TextPassView textPassView) {
        this.textPassView = textPassView;
        this.textPassModel = new TextPassModelImpl();
    }

    @Override
    public void onCreateSuccess() {
        textPassView.onCreateSuccess();
    }

    @Override
    public void onCreateError(String msg) {
        textPassView.onCreateError(msg);
    }

    @Override
    public void createPass(String password) {
        textPassModel.createPass(password, this);
    }
}

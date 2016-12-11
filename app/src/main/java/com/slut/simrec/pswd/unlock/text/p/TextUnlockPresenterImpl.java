package com.slut.simrec.pswd.unlock.text.p;

import com.slut.simrec.pswd.unlock.text.m.TextUnlockModel;
import com.slut.simrec.pswd.unlock.text.m.TextUnlockModelImpl;
import com.slut.simrec.pswd.unlock.text.v.TextUnlockView;

/**
 * Created by 七月在线科技 on 2016/12/11.
 */

public class TextUnlockPresenterImpl implements TextUnlockPresenter, TextUnlockModel.OnValidateListener {

    private TextUnlockModel textUnlockModel;
    private TextUnlockView textUnlockView;

    public TextUnlockPresenterImpl(TextUnlockView textUnlockView) {
        this.textUnlockView = textUnlockView;
        this.textUnlockModel = new TextUnlockModelImpl();
    }

    @Override
    public void onValidateSuccess() {
        textUnlockView.onValidateSuccess();
    }

    @Override
    public void onValidateFailed() {
        textUnlockView.onValidateFailed();
    }

    @Override
    public void onValidateError(String msg) {
        textUnlockView.onValidateError(msg);
    }

    @Override
    public void validate(String password) {
        textUnlockModel.validate(password, this);
    }
}

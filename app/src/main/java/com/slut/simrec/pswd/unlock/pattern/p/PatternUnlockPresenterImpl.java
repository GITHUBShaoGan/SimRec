package com.slut.simrec.pswd.unlock.pattern.p;

import com.slut.simrec.pswd.unlock.pattern.m.PatternUnlockModel;
import com.slut.simrec.pswd.unlock.pattern.m.PatternUnlockModelImpl;
import com.slut.simrec.pswd.unlock.pattern.v.PatternUnlockView;

import java.util.List;

import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by 七月在线科技 on 2016/12/19.
 */

public class PatternUnlockPresenterImpl implements PatternUnlockPresenter,PatternUnlockModel.OnValidateListener {

    private PatternUnlockView patternUnlockView;
    private PatternUnlockModel patternUnlockModel;

    public PatternUnlockPresenterImpl(PatternUnlockView patternUnlockView) {
        this.patternUnlockView = patternUnlockView;
        this.patternUnlockModel = new PatternUnlockModelImpl();
    }

    @Override
    public void onValidateSuccess() {
        patternUnlockView.onValidateSuccess();
    }

    @Override
    public void onValidateError(String msg) {
        patternUnlockView.onValidateError(msg);
    }

    @Override
    public void validate(List<PatternView.Cell> patternList) {
        patternUnlockModel.validate(patternList,this);
    }
}

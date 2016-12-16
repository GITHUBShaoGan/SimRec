package com.slut.simrec.pswd.master.pattern.p;

import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.pswd.master.pattern.m.PatternPassModel;
import com.slut.simrec.pswd.master.pattern.m.PatternPassModelImpl;
import com.slut.simrec.pswd.master.pattern.v.PatternPassView;

/**
 * Created by 七月在线科技 on 2016/12/16.
 */

public class PatternPassPresenterImpl implements PatternPassPresenter,PatternPassModel.OnPatternSetListener {

    private PatternPassModel patternPassModel;
    private PatternPassView patternPassView;

    public PatternPassPresenterImpl(PatternPassView patternPassView) {
        this.patternPassView = patternPassView;
        this.patternPassModel = new PatternPassModelImpl();
    }

    @Override
    public void onPatternSetSuccess() {
        patternPassView.onPatternSetSuccess();
    }

    @Override
    public void onPatternSetError(String msg) {
        patternPassView.onPatternSetError(msg);
    }

    @Override
    public void setPattern(String pattern) {
        patternPassModel.setPattern(pattern,this);
    }
}

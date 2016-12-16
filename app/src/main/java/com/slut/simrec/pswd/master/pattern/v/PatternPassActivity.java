package com.slut.simrec.pswd.master.pattern.v;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.slut.simrec.App;
import com.slut.simrec.pswd.master.pattern.p.PatternPassPresenter;
import com.slut.simrec.pswd.master.pattern.p.PatternPassPresenterImpl;
import com.slut.simrec.utils.ToastUtils;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;
import me.zhanghai.android.patternlock.SetPatternActivity;

public class PatternPassActivity extends SetPatternActivity implements PatternPassView {

    private PatternPassPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new PatternPassPresenterImpl(this);
    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        super.onSetPattern(pattern);
        String password = PatternUtils.patternToSha1String(pattern);
        presenter.setPattern(password);
    }

    @Override
    public void onPatternSetSuccess() {
        App.setIsPswdFunctionLocked(false);
        Intent intent = getIntent();
        if(intent!=null){
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public void onPatternSetError(String msg) {
        ToastUtils.showShort(msg);
    }
}


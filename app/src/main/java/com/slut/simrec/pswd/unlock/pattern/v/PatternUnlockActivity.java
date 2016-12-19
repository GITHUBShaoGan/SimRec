package com.slut.simrec.pswd.unlock.pattern.v;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.pswd.unlock.pattern.p.PatternUnlockPresenter;
import com.slut.simrec.pswd.unlock.pattern.p.PatternUnlockPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.patternlock.ConfirmPatternActivity;
import me.zhanghai.android.patternlock.PatternView;

public class PatternUnlockActivity extends AppCompatActivity implements PatternUnlockView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.patternView)
    PatternView patternView;
    @BindView(R.id.tips)
    TextView tips;

    private PatternUnlockPresenter patternUnlockPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_unlock);
        ButterKnife.bind(this);
        App.getInstances().addActivity(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        patternUnlockPresenter = new PatternUnlockPresenterImpl(this);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if (intent != null) {
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
            }
        });
        patternView.setOnPatternListener(new PatternView.OnPatternListener() {
            @Override
            public void onPatternStart() {

            }

            @Override
            public void onPatternCleared() {

            }

            @Override
            public void onPatternCellAdded(List<PatternView.Cell> pattern) {
                if (pattern.size() < 4) {
                    tips.setText(R.string.tips_pattern_unlock_length_error);
                    tips.setTextColor(Color.parseColor("#FF0042"));
                }else{
                    tips.setText(R.string.tips_pattern_unlock);
                    tips.setTextColor(Color.parseColor("#2A3245"));
                }
            }

            @Override
            public void onPatternDetected(List<PatternView.Cell> pattern) {
                patternUnlockPresenter.validate(pattern);
                patternView.clearPattern();
            }
        });
    }


    @Override
    public void onValidateSuccess() {
        Intent intent = getIntent();
        App.setIsPswdFunctionLocked(false);
        if (intent != null) {
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onValidateError(String msg) {
        tips.setText(msg);
        tips.setTextColor(Color.parseColor("#FF0042"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = getIntent();
            if (intent != null) {
                setResult(RESULT_CANCELED, intent);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
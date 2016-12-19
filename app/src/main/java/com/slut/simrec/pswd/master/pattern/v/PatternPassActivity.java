package com.slut.simrec.pswd.master.pattern.v;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.pswd.master.pattern.p.PatternPassPresenter;
import com.slut.simrec.pswd.master.pattern.p.PatternPassPresenterImpl;
import com.slut.simrec.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;
import me.zhanghai.android.patternlock.SetPatternActivity;

public class PatternPassActivity extends AppCompatActivity implements PatternPassView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.patternView)
    PatternView patternView;
    private PatternPassPresenter presenter;
    private int count = 0;
    private String mPattern;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_pass);
        ButterKnife.bind(this);
        App.getInstances().addActivity(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new PatternPassPresenterImpl(this);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                    tips.setText(R.string.tips_pattern_set_length_error);
                    tips.setTextColor(Color.parseColor("#FF0042"));
                } else {
                    if (count == 0) {
                        tips.setText(R.string.tips_pattern_set_first);
                        tips.setTextColor(Color.parseColor("#2A3245"));
                    } else if (count == 1) {
                        tips.setText(R.string.tips_pattern_set_second);
                        tips.setTextColor(Color.parseColor("#2A3245"));
                    }
                }
            }

            @Override
            public void onPatternDetected(List<PatternView.Cell> pattern) {
                if (count == 0) {
                    mPattern = PatternUtils.patternToSha1String(pattern);
                    patternView.clearPattern();
                    tips.setText(R.string.tips_pattern_set_second);
                    count++;
                } else if (count == 1) {
                    if (mPattern.equals(PatternUtils.patternToSha1String(pattern))) {
                        tips.setTextColor(Color.parseColor("#2A3245"));
                        presenter.setPattern(mPattern);
                    } else {
                        tips.setText(R.string.tips_pattern_set_not_equal);
                        tips.setTextColor(Color.parseColor("#FF0042"));
                    }
                }
            }
        });
    }

    @Override
    public void onPatternSetSuccess() {
        App.setIsPswdFunctionLocked(false);
        Intent intent = getIntent();
        if (intent != null) {
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onPatternSetError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pattern_pass, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                count = 0;
                mPattern = "";
                patternView.clearPattern();
                tips.setTextColor(Color.parseColor("#2A3245"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


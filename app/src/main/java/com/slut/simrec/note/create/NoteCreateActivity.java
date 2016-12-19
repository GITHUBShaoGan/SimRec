package com.slut.simrec.note.create;

import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.slut.simrec.R;
import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDEditText;
import com.yydcdut.rxmarkdown.RxMarkdown;
import com.yydcdut.rxmarkdown.callback.OnLinkClickCallback;
import com.yydcdut.rxmarkdown.factory.EditFactory;
import com.yydcdut.rxmarkdown.factory.TextFactory;
import com.yydcdut.rxmarkdown.loader.DefaultLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class NoteCreateActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.edit)
    RxMDEditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        final RxMDConfiguration rxMDConfiguration = new RxMDConfiguration.Builder(this)
                .setDefaultImageSize(100, 100)//default image width & height
                .setBlockQuotesColor(Color.LTGRAY)//default color of block quotes
                .setHeader1RelativeSize(1.6f)//default relative size of header1
                .setHeader2RelativeSize(1.5f)//default relative size of header2
                .setHeader3RelativeSize(1.4f)//default relative size of header3
                .setHeader4RelativeSize(1.3f)//default relative size of header4
                .setHeader5RelativeSize(1.2f)//default relative size of header5
                .setHeader6RelativeSize(1.1f)//default relative size of header6
                .setHorizontalRulesColor(Color.LTGRAY)//default color of horizontal rules's background
                .setInlineCodeBgColor(Color.LTGRAY)//default color of inline code's background
                .setCodeBgColor(Color.LTGRAY)//default color of code's background
                .setTodoColor(Color.DKGRAY)//default color of todo
                .setTodoDoneColor(Color.DKGRAY)//default color of done
                .setUnOrderListColor(Color.BLACK)//default color of unorder list
                .setLinkColor(Color.RED)//default color of link text
                .setLinkUnderline(true)//default value of whether displays link underline
                .setRxMDImageLoader(new DefaultLoader(this))//default image loader
                .setDebug(true)//default value of debug
                .setOnLinkClickCallback(new OnLinkClickCallback() {//link click callback
                    @Override
                    public void onLinkClicked(View view, String link) {
                    }
                })
                .build();
        RxMarkdown.live(content)
                .config(rxMDConfiguration)
                .factory(EditFactory.create())
                .intoObservable()
                .subscribe();

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                RxMarkdown.with(editable.toString(), NoteCreateActivity.this)
                        .config(rxMDConfiguration)
                        .factory(TextFactory.create())
                        .intoObservable()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<CharSequence>() {
                            @Override
                            public void onCompleted() {}

                            @Override
                            public void onError(Throwable e) {}

                            @Override
                            public void onNext(CharSequence charSequence) {
                                text.setText(charSequence, TextView.BufferType.SPANNABLE);
                            }
                        });
            }
        });


    }
}

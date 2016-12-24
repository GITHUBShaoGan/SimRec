package com.slut.simrec.note.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slut.simrec.R;
import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.note.edit.v.NoteCreateActivity;
import com.slut.simrec.widget.MarkdownPreviewView;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MarkdownPreviewView content;
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;

    private Note note;
    private ArrayList<NoteLabel> noteLabelArrayList;

    public static final String EXTRA_NOTE = "note";
    public static final String EXTRA_NOTE_LABEL = "label";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        content = (MarkdownPreviewView)findViewById(R.id.markdown);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_NOTE) && intent.hasExtra(EXTRA_NOTE_LABEL)) {
            note = intent.getParcelableExtra(EXTRA_NOTE);
            noteLabelArrayList = intent.getParcelableArrayListExtra(EXTRA_NOTE_LABEL);

            if (note != null) {
                toolbar.setTitle(note.getTitle() + "");
                content.parseMarkdown(note.getContent() + "", true);
            }
            if (noteLabelArrayList != null) {
                flowLayout.removeAllViews();
                for (NoteLabel noteLabel : noteLabelArrayList) {
                    View view = LayoutInflater.from(this).inflate(R.layout.label, new LinearLayout(this), false);
                    TextView textView = (TextView) view.findViewById(R.id.label);
                    textView.setText(noteLabel.getName() + "");
                    flowLayout.addView(view);
                }
            }
        } else {
            finish();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(this, NoteCreateActivity.class);
                intent.putExtra(NoteCreateActivity.EXTRA_NOTE, note);
                intent.putParcelableArrayListExtra(NoteCreateActivity.EXTRA_NOTE_LABEL, noteLabelArrayList);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

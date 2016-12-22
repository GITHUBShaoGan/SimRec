package com.slut.simrec.note.label.option.v;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.slut.simrec.R;
import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.note.label.option.adapter.LabelOptionAdapter;
import com.slut.simrec.note.label.option.p.LabelOptionPresenter;
import com.slut.simrec.note.label.option.p.LabelOptionPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LabelOptionsActivity extends AppCompatActivity implements LabelOptionView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    LinearLayout empty;

    private LinearLayoutManager layoutManager;
    private List<NoteLabel> noteLabelList;
    private List<Boolean> isCheckList;

    private ArrayList<NoteLabel> extraNoteLabels;

    private LabelOptionAdapter adapter;
    public static final String EXTRA_LIST = "label_list";

    private LabelOptionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_option);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extraNoteLabels = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_LIST)) {
                extraNoteLabels = intent.getParcelableArrayListExtra(EXTRA_LIST);
            }
        }

        noteLabelList = new ArrayList<>();
        isCheckList = new ArrayList<>();
        adapter = new LabelOptionAdapter();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        presenter = new LabelOptionPresenterImpl(this);
        presenter.loadData(extraNoteLabels);
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
        getMenuInflater().inflate(R.menu.label_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                View v = LayoutInflater.from(this).inflate(R.layout.view_dialog_add_note_label, new LinearLayout(this), false);
                final EditText name = (EditText) v.findViewById(R.id.name);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.title_dialog_new_label);
                builder.setView(v);
                builder.setPositiveButton(R.string.action_dialog_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String text = name.getText().toString().trim();
                        presenter.create(text);
                    }
                });
                builder.setNegativeButton(R.string.action_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                Dialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                final Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setEnabled(false);
                name.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String name = editable.toString().trim();
                        if (name.isEmpty())
                            positiveButton.setEnabled(false);
                        else
                            positiveButton.setEnabled(true);
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadSuccess(List<NoteLabel> noteLabelList, List<Boolean> isCheckList) {
        this.noteLabelList = noteLabelList;
        this.isCheckList = isCheckList;
        adapter.setNoteLabelList(this.noteLabelList);
        adapter.setIsCheckedList(this.isCheckList);
        adapter.notifyDataSetChanged();

        showOrHide();
    }

    @Override
    public void onLoadError(String msg) {
        showOrHide();
    }

    @Override
    public void onCreateSuccess(NoteLabel noteLabel) {
        this.noteLabelList.add(0, noteLabel);
        adapter.setNoteLabelList(this.noteLabelList);
        adapter.notifyItemInserted(0);

        showOrHide();
    }

    @Override
    public void onCreateError(String msg) {
        showOrHide();
    }

    private void showOrHide() {
        if (adapter != null) {
            List<NoteLabel> noteLabelList = adapter.getNoteLabelList();
            if (noteLabelList != null && !noteLabelList.isEmpty()) {
                empty.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                empty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            }
        } else {
            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }
}

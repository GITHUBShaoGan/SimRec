package com.slut.simrec.note.create;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slut.simrec.R;
import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.note.create.adapter.MyPagerAdapter;
import com.slut.simrec.note.label.option.v.LabelOptionsActivity;
import com.slut.simrec.utils.StringUtils;
import com.slut.simrec.utils.SystemUtils;
import com.slut.simrec.widget.MarkdownPreviewView;
import com.slut.simrec.widget.TabIconView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteCreateActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab)
    TabIconView tabIconView;

    private EditText title;
    private TextView showTitle;
    private EditText content;
    private MarkdownPreviewView showContent;

    private InputMethodManager inputMethodManager;

    private MyPagerAdapter pagerAdapter;

    private static final int REQUEST_SELECT_PIC_KITKAT = 1078;
    private static final int REQUEST_SELECT_PIC = 1098;

    private static final String H1_MD_PREFIX = "# ";
    private static final String H2_MD_PREFIX = "## ";
    private static final String H3_MD_PREFIX = "### ";
    private static final String H4_MD_PREFIX = "#### ";
    private static final String H5_MD_PREFIX = "##### ";
    private static final String H6_MD_PREFIX = "###### ";
    private static final String PICS_MD_PREFIX_START = "![image](";
    private static final String PICS_MD_PREFIX_END = ")";
    private static final String BOLD_MD_PREFIX = "****";
    private static final String BOLD_MD_PREFIX_SINGLE = "**";
    private static final String ITALIC_MD_PREFIX = "**";
    private static final String ITALIC_MD_PREFIX_SINGLE = "*";
    private static final String MEDIUM_LINE_MD_PREFIX = "~~~~";
    private static final String MEDIUM_LINE_MD_PREFIX_SINGLE = "~~";
    private static final String DIVIDER_MD_PREFIX = "---";
    private static final String UNSORT_LIST_MD_PREFIX = "* ";
    private static final String SORT_LIST_MD_PREFIX = "1. ";
    private static final String QUOTE_LIST_MD_PREFIX = "> ";
    private static final String CODE_BLOCK_MD_PREFIX = "``";
    private static final String CONSOLE_MD_PREFIX_SINGLE = "```";

    private static final int TYPE_BOLD = 1;
    private static final int TYPE_ITALIC = 2;
    private static final int TYPE_MEDIUM_LINE = 3;
    private static final int TYPE_UNSORTLIST = 4;
    private static final int TYPE_SORTLIST = 5;
    private static final int TYPE_CODEBLOCK = 6;
    private static final int TYPE_QUOTE = 7;
    private static final int TYPE_CONSOLE = 8;

    private static final int REQUEST_SET_LABELS = 1030;
    private ArrayList<NoteLabel> noteLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);
        ButterKnife.bind(this);
        initView();
        initListener();
        initTab();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View editFather = LayoutInflater.from(this).inflate(R.layout.view_note_create_edit, new LinearLayout(this), false);
        View showFather = LayoutInflater.from(this).inflate(R.layout.view_note_create_show, new LinearLayout(this), false);
        title = (EditText) editFather.findViewById(R.id.editTitle);
        content = (EditText) editFather.findViewById(R.id.editContent);
        showTitle = (TextView) showFather.findViewById(R.id.showTitle);
        showContent = (MarkdownPreviewView) showFather.findViewById(R.id.showContent);

        inputMethodManager =
                (InputMethodManager) content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        List<View> viewList = new ArrayList<>();
        viewList.add(editFather);
        viewList.add(showFather);

        pagerAdapter = new MyPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        noteLabels = new ArrayList<>();
    }

    private void initTab() {
        tabIconView.addTab(R.drawable.ic_format_list_bulleted_white_24dp, R.id.ids_operate_markdown_unsortlist, this);
        tabIconView.addTab(R.drawable.ic_format_list_numbers_white_24dp, R.id.ids_operate_markdown_sortlist, this);
        tabIconView.addTab(R.drawable.pics, R.id.ids_operate_markdown_pics, this);
        tabIconView.addTab(R.drawable.enter, R.id.ids_operate_markdown_enter, this);
        tabIconView.addTab(R.drawable.ic_format_bold_white_24dp, R.id.ids_operate_markdown_bold, this);
        tabIconView.addTab(R.drawable.ic_format_italic_white_24dp, R.id.ids_operate_markdown_italic, this);
        tabIconView.addTab(R.drawable.icon_console, R.id.ids_operate_markdown_console, this);
        tabIconView.addTab(R.drawable.code_block, R.id.ids_operate_markdown_codeblock, this);
        tabIconView.addTab(R.drawable.quote, R.id.ids_operate_markdown_quote, this);
        tabIconView.addTab(R.drawable.divider, R.id.ids_operate_markdown_divider, this);
        tabIconView.addTab(R.drawable.medium_lined, R.id.ids_operate_markdown_mediumline, this);
        tabIconView.addTab(R.drawable.table, R.id.ids_operate_markdown_table, this);
        tabIconView.addTab(R.drawable.ic_format_header_1_white_24dp, R.id.ids_operate_markdown_h1, this);
        tabIconView.addTab(R.drawable.ic_format_header_2_white_24dp, R.id.ids_operate_markdown_h2, this);
        tabIconView.addTab(R.drawable.ic_format_header_3_white_24dp, R.id.ids_operate_markdown_h3, this);
        tabIconView.addTab(R.drawable.ic_format_header_4_white_24dp, R.id.ids_operate_markdown_h4, this);
        tabIconView.addTab(R.drawable.ic_format_header_5_white_24dp, R.id.ids_operate_markdown_h5, this);
        tabIconView.addTab(R.drawable.ic_format_header_6_white_24dp, R.id.ids_operate_markdown_h6, this);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        //编辑状态
                        inputMethodManager.showSoftInput(content, InputMethodManager.SHOW_FORCED);
                        break;
                    case 1:
                        //预览状态
                        String t = title.getText().toString();
                        showTitle.setText(t);
                        String c = content.getText().toString();
                        showContent.parseMarkdown(c, true);

                        // 得到InputMethodManager的实例
                        inputMethodManager.hideSoftInputFromWindow(content.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SELECT_PIC_KITKAT:
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            String path = SystemUtils.getPath(uri);
                            if (path != null) {
                                addPic(path);
                            }
                        }
                    }
                    break;
                case REQUEST_SELECT_PIC:
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        //弹出软键盘
        switch (view.getId()) {
            case R.id.ids_operate_markdown_h1:
                addHeader(H1_MD_PREFIX);
                break;
            case R.id.ids_operate_markdown_h2:
                addHeader(H2_MD_PREFIX);
                break;
            case R.id.ids_operate_markdown_h3:
                addHeader(H3_MD_PREFIX);
                break;
            case R.id.ids_operate_markdown_h4:
                addHeader(H4_MD_PREFIX);
                break;
            case R.id.ids_operate_markdown_h5:
                addHeader(H5_MD_PREFIX);
                break;
            case R.id.ids_operate_markdown_h6:
                addHeader(H6_MD_PREFIX);
                break;
            case R.id.ids_operate_markdown_pics:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    startActivityForResult(intent, REQUEST_SELECT_PIC_KITKAT);
                } else {
                    startActivityForResult(intent, REQUEST_SELECT_PIC);
                }
                break;
            case R.id.ids_operate_markdown_enter:
                enter();
                break;
            case R.id.ids_operate_markdown_bold:
                changeSelection(TYPE_BOLD);
                break;
            case R.id.ids_operate_markdown_italic:
                changeSelection(TYPE_ITALIC);
                break;
            case R.id.ids_operate_markdown_mediumline:
                changeSelection(TYPE_MEDIUM_LINE);
                break;
            case R.id.ids_operate_markdown_divider:
                addDivider();
                break;
            case R.id.ids_operate_markdown_quote:
                addAtNextLine(TYPE_QUOTE);
                break;
            case R.id.ids_operate_markdown_sortlist:
                addAtNextLine(TYPE_SORTLIST);
                break;
            case R.id.ids_operate_markdown_unsortlist:
                addAtNextLine(TYPE_UNSORTLIST);
                break;
            case R.id.ids_operate_markdown_codeblock:
                addCodeBlock(TYPE_CODEBLOCK);
                break;
            case R.id.ids_operate_markdown_console:
                addCodeBlock(TYPE_CONSOLE);
                break;
            case R.id.ids_operate_markdown_table:
                View v = LayoutInflater.from(this).inflate(R.layout.view_dialog_table_config, new LinearLayout(this), false);
                final TextInputLayout tilRows = (TextInputLayout) v.findViewById(R.id.row);
                final TextInputLayout tilColumns = (TextInputLayout) v.findViewById(R.id.column);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.title_dialog_table);
                builder.setView(v);
                builder.setPositiveButton(R.string.action_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String rows = tilRows.getEditText().getText().toString().trim();
                        String columns = tilColumns.getEditText().getText().toString().trim();
                        addTable(Integer.valueOf(rows), Integer.valueOf(columns));
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
                tilRows.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String rows = editable.toString().trim();
                        String columns = tilColumns.getEditText().getText().toString();
                        if (StringUtils.isInteger(rows) && StringUtils.isInteger(columns)) {
                            if (Integer.valueOf(rows) > 0 && Integer.valueOf(columns) > 0) {
                                positiveButton.setEnabled(true);
                            }
                        } else {
                            positiveButton.setEnabled(false);
                        }
                    }
                });
                tilColumns.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String rows = editable.toString().trim();
                        String columns = tilRows.getEditText().getText().toString();
                        if (StringUtils.isInteger(rows) && StringUtils.isInteger(columns)) {
                            if (Integer.valueOf(rows) > 0 && Integer.valueOf(columns) > 0) {
                                positiveButton.setEnabled(true);
                            }
                        } else {
                            positiveButton.setEnabled(false);
                        }
                    }
                });
                break;
        }
    }

    private void addTable(int rowCount, int columnCount) {
        String headerPrefix = "";
        String formatPrefix = "";
        String rowPrefix = "";
        for (int i = 0; i < columnCount; i++) {
            headerPrefix += "| Header ";
            formatPrefix += "|:------:";
            rowPrefix += "|        ";
        }
        headerPrefix += "|";
        formatPrefix += "|";
        rowPrefix += "|";
        String textBefore = content.getText().toString();
        int selectionStartBefore = content.getSelectionStart();
        int selectionEndBefore = content.getSelectionEnd();
        String startText = selectionStartBefore == selectionEndBefore ? textBefore.substring(0, selectionStartBefore) : textBefore.substring(0, selectionEndBefore);
        String endText = selectionStartBefore == selectionEndBefore ? textBefore.substring(selectionStartBefore) : textBefore.substring(selectionEndBefore);
        int lineCount = content.getLineCount();//现在是第几行
        if (lineCount == 0 && TextUtils.isEmpty(textBefore)) {
            String text = headerPrefix + "\n" + formatPrefix + "\n";
            for (int i = 0; i < rowCount; i++) {
                text += rowPrefix + "\n";
            }
            content.setText(text);
        } else {
            String text = startText + "\n\n" + headerPrefix + "\n" + formatPrefix + "\n";
            for (int i = 0; i < rowCount; i++) {
                text += rowPrefix + "\n";
            }
            text += "\n" + endText;
            content.setText(text);
        }
    }

    private void addCodeBlock(int type) {
        String textBefore = content.getText().toString();
        int selectionStartBefore = content.getSelectionStart();
        int selectionEndBefore = content.getSelectionEnd();
        String startText = selectionStartBefore == selectionEndBefore ? textBefore.substring(0, selectionStartBefore) : textBefore.substring(0, selectionEndBefore);
        String endText = selectionStartBefore == selectionEndBefore ? textBefore.substring(selectionStartBefore) : textBefore.substring(selectionEndBefore);
        int lineCount = content.getLineCount();//现在是第几行
        if (lineCount == 1 && TextUtils.isEmpty(textBefore)) {
            if (type == TYPE_CODEBLOCK) {
                content.setText(CODE_BLOCK_MD_PREFIX);
                content.setSelection(CODE_BLOCK_MD_PREFIX.length() - 1);
            }
            if (type == TYPE_CONSOLE) {
                String text = CONSOLE_MD_PREFIX_SINGLE + "\n";
                content.setText(text + "\n" + CONSOLE_MD_PREFIX_SINGLE);
                content.setSelection(text.length());
            }
        } else {
            if (type == TYPE_CODEBLOCK) {
                String text = startText + CODE_BLOCK_MD_PREFIX;
                content.setText(text + endText);
                content.setSelection(text.length() - 1);
            }
            if (type == TYPE_CONSOLE) {
                String text = startText + CONSOLE_MD_PREFIX_SINGLE + "\n";
                content.setText(text + "\n" + endText);
                content.setSelection(text.length());
            }
        }
    }

    private void addAtNextLine(int type) {
        String textBefore = content.getText().toString();
        int selectionStartBefore = content.getSelectionStart();
        int selectionEndBefore = content.getSelectionEnd();
        String startText = selectionStartBefore == selectionEndBefore ? textBefore.substring(0, selectionStartBefore) : textBefore.substring(0, selectionEndBefore);
        String endText = selectionStartBefore == selectionEndBefore ? textBefore.substring(selectionStartBefore) : textBefore.substring(selectionEndBefore);
        int lineCount = content.getLineCount();//现在是第几行
        if (lineCount == 1 && TextUtils.isEmpty(textBefore)) {
            if (type == TYPE_QUOTE) {
                String text = QUOTE_LIST_MD_PREFIX;
                content.setText(text);
                content.setSelection(text.length());
            }
            if (type == TYPE_SORTLIST) {
                String text = SORT_LIST_MD_PREFIX;
                content.setText(text);
                content.setSelection(text.length());
            }
            if (type == TYPE_UNSORTLIST) {
                String text = UNSORT_LIST_MD_PREFIX;
                content.setText(text);
                content.setSelection(text.length());
            }
        } else {
            if (type == TYPE_QUOTE) {
                String text = startText + "\n" + QUOTE_LIST_MD_PREFIX;
                content.setText(text + "\n" + endText);
                content.setSelection(text.length());
            }
            if (type == TYPE_SORTLIST) {
                String text = startText + "\n" + SORT_LIST_MD_PREFIX;
                content.setText(text + "\n" + endText);
                content.setSelection(text.length());
            }
            if (type == TYPE_UNSORTLIST) {
                String text = startText + "\n" + UNSORT_LIST_MD_PREFIX;
                content.setText(text + "\n" + endText);
                content.setSelection(text.length());
            }
        }
    }

    private void addDivider() {
        String textBefore = content.getText().toString();
        int selectionStartBefore = content.getSelectionStart();
        int selectionEndBefore = content.getSelectionEnd();
        String startText = selectionStartBefore == selectionEndBefore ? textBefore.substring(0, selectionStartBefore) : textBefore.substring(0, selectionEndBefore);
        String endText = selectionStartBefore == selectionEndBefore ? textBefore.substring(selectionStartBefore) : textBefore.substring(selectionEndBefore);
        int lineCount = content.getLineCount();//现在是第几行
        if (lineCount == 1 && TextUtils.isEmpty(textBefore)) {
            String text = DIVIDER_MD_PREFIX + "\n";
            content.setText(text);
            content.setSelection(text.length());
        } else {
            String text = startText + "\n" + DIVIDER_MD_PREFIX + "\n";
            content.setText(text + endText);
            content.setSelection(text.length());
        }
    }

    private void changeSelection(int type) {
        String textBefore = content.getText().toString();
        int selectionStartBefore = content.getSelectionStart();
        int selectionEndBefore = content.getSelectionEnd();
        if (selectionEndBefore == selectionStartBefore) {
            String startText = textBefore.substring(0, selectionStartBefore);
            String endText = textBefore.substring(selectionStartBefore);
            if (type == TYPE_BOLD) {
                String text = startText + BOLD_MD_PREFIX;
                content.setText(text + endText);
                content.setSelection(text.length() - 2);
            }
            if (type == TYPE_ITALIC) {
                String text = startText + ITALIC_MD_PREFIX;
                content.setText(text + endText);
                content.setSelection(text.length() - 1);
            }
            if (type == TYPE_MEDIUM_LINE) {
                String text = startText + MEDIUM_LINE_MD_PREFIX;
                content.setText(text + endText);
                content.setSelection(text.length() - 2);
            }
        } else {
            String startText = textBefore.substring(0, selectionStartBefore);
            String selectText = textBefore.substring(selectionStartBefore, selectionEndBefore);
            String endText = textBefore.substring(selectionEndBefore);
            if (type == TYPE_BOLD) {
                String text = startText + BOLD_MD_PREFIX_SINGLE + selectText + BOLD_MD_PREFIX_SINGLE;
                content.setText(text + endText);
                content.setSelection(text.length());
            }
            if (type == TYPE_ITALIC) {
                String text = startText + ITALIC_MD_PREFIX_SINGLE + selectText + ITALIC_MD_PREFIX_SINGLE;
                content.setText(text + endText);
                content.setSelection(text.length());
            }
            if (type == TYPE_MEDIUM_LINE) {
                String text = startText + MEDIUM_LINE_MD_PREFIX_SINGLE + selectText + MEDIUM_LINE_MD_PREFIX_SINGLE;
                content.setText(text + endText);
                content.setSelection(text.length());
            }
        }
    }

    /**
     * 换行
     */
    private void enter() {
        String textBefore = content.getText().toString();
        int selectionStartBefore = content.getSelectionStart();
        int selectionEndBefore = content.getSelectionEnd();
        String startText = selectionStartBefore == selectionEndBefore ? textBefore.substring(0, selectionStartBefore) : textBefore.substring(0, selectionEndBefore);
        String endText = selectionStartBefore == selectionEndBefore ? textBefore.substring(selectionStartBefore) : textBefore.substring(selectionEndBefore);
        String text = startText + "\n";
        content.setText(text + endText);
        content.setSelection(text.length());
    }

    /**
     * 添加header
     *
     * @param prefix
     */
    private void addHeader(String prefix) {
        String textBefore = content.getText().toString();
        int selectionStartBefore = content.getSelectionStart();
        int selectionEndBefore = content.getSelectionEnd();
        String startText = selectionStartBefore == selectionEndBefore ? textBefore.substring(0, selectionStartBefore) : textBefore.substring(0, selectionEndBefore);
        String endText = selectionStartBefore == selectionEndBefore ? textBefore.substring(selectionStartBefore) : textBefore.substring(selectionEndBefore);
        int lineCount = content.getLineCount();//现在是第几行
        if (lineCount == 1 && TextUtils.isEmpty(textBefore)) {
            //如果在内容开头添加的话，直接添加即可
            content.setText(prefix);
            content.setSelection(prefix.length());
        } else {
            //否则新起一行
            String text = startText + "\n" + prefix;
            content.setText(text + "\n" + endText);
            content.setSelection(text.length());
        }
    }

    /**
     * 添加图片
     *
     * @param path
     */
    private void addPic(String path) {
        String textBefore = content.getText().toString();
        int selectionStartBefore = content.getSelectionStart();
        int selectionEndBefore = content.getSelectionEnd();
        String startText = selectionStartBefore == selectionEndBefore ? textBefore.substring(0, selectionStartBefore) : textBefore.substring(0, selectionEndBefore);
        String endText = selectionStartBefore == selectionEndBefore ? textBefore.substring(selectionStartBefore) : textBefore.substring(selectionEndBefore);
        int lineCount = content.getLineCount();//现在是第几行
        if (lineCount == 1 && TextUtils.isEmpty(textBefore)) {
            content.setText(PICS_MD_PREFIX_START + path + PICS_MD_PREFIX_END);
        } else {
            String text = startText + "\n" + PICS_MD_PREFIX_START + path + PICS_MD_PREFIX_END + "\n";
            content.setText(text + endText);
            content.setSelection(text.length());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.label:
                Intent intent = new Intent(this, LabelOptionsActivity.class);
                intent.putExtra(LabelOptionsActivity.EXTRA_LIST, noteLabels);
                startActivityForResult(intent, REQUEST_SET_LABELS);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

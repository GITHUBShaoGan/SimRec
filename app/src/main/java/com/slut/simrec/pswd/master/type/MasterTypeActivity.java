package com.slut.simrec.pswd.master.type;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.slut.simrec.R;
import com.slut.simrec.pswd.master.grid.v.GridPassActivity;
import com.slut.simrec.pswd.master.pattern.PatternPassActivity;
import com.slut.simrec.pswd.master.text.v.TextPassActivity;
import com.slut.simrec.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterTypeActivity extends AppCompatActivity implements LockTypeAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private LockTypeAdapter adapter;
    private LinearLayoutManager layoutManager;

    private static final int REQUEST_SET_MASTER = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_type);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);
        adapter = new LockTypeAdapter();
        adapter.setOnItemClickListener(this);
        adapter.setLockTypeList(initLockTypeList());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private List<LockType> initLockTypeList() {
        List<LockType> lockTypes = new ArrayList<>();
        String[] titleArr = ResUtils.getStringArray(R.array.title_lock_type);
        String[] descArr = ResUtils.getStringArray(R.array.description_lock_type);
        int[] imageArr = {R.drawable.fingerprint, R.drawable.fingerprint, R.drawable.fingerprint, R.drawable.fingerprint};
        for (int i = 0; i < titleArr.length; i++) {
            LockType lockType = new LockType(titleArr[i], descArr[i], imageArr[i]);
            lockTypes.add(lockType);
        }
        return lockTypes;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SET_MASTER:
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                //指纹识别
                break;
            case 1:
                Intent intentOpenGrid = new Intent(this, GridPassActivity.class);
                startActivityForResult(intentOpenGrid, REQUEST_SET_MASTER);
                break;
            case 2:
                Intent intentOpenPattern = new Intent(this, PatternPassActivity.class);
                startActivityForResult(intentOpenPattern, REQUEST_SET_MASTER);
                break;
            case 3:
                Intent intentOpenText = new Intent(this, TextPassActivity.class);
                startActivityForResult(intentOpenText, REQUEST_SET_MASTER);
                break;
        }
    }
}

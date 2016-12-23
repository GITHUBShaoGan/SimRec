package com.slut.simrec.main.v;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.fingerprint.FingerprintHelper;
import com.slut.simrec.fingerprint.OnFingerPrintAuthListener;
import com.slut.simrec.main.adapter.MainPagerAdapter;
import com.slut.simrec.main.fragment.note.v.NoteFragment;
import com.slut.simrec.main.fragment.pay.PayFragment;
import com.slut.simrec.main.fragment.pswd.v.PswdFragment;
import com.slut.simrec.main.p.MainPresenter;
import com.slut.simrec.main.p.MainPresenterImpl;
import com.slut.simrec.note.create.v.NoteCreateActivity;
import com.slut.simrec.pswd.category.defaultcat.v.DefaultCatActivity;
import com.slut.simrec.pswd.master.type.v.MasterTypeActivity;
import com.slut.simrec.pswd.search.v.PassSearchActivity;
import com.slut.simrec.pswd.unlock.grid.v.GridUnlockActivity;
import com.slut.simrec.pswd.unlock.pattern.v.PatternUnlockActivity;
import com.slut.simrec.pswd.unlock.text.v.TextUnlockActivity;
import com.slut.simrec.utils.ResUtils;
import com.slut.simrec.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private MainPagerAdapter pagerAdapter;
    private MainPresenter presenter;

    private static final int REQUEST_CREATE_MASTER_FOR_ADD = 1000;
    private static final int REQUEST_CREATE_MASTER_FOR_SEARCH = 6005;
    private static final int REQUEST_UNLOCK_FOR_ADD = 2000;
    private static final int REQUEST_UNLOCK_FOR_SEARCH = 5000;
    public static final int REQUEST_CREATE_PASSWORD = 3000;
    public static final int REQUEST_UNLOCK_COPY = 4000;
    //控件点击类，0代表点击添加按钮，1代表点击搜索按菜单
    private static final int CLICK_ADD = 0, CLICK_SEARCH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        App.getInstances().addActivity(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), initTitles(), initFragments());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

        presenter = new MainPresenterImpl(this);
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        int currentItemId = viewPager.getCurrentItem();
        switch (currentItemId) {
            case 0:
                //密码
                presenter.onUIClick(CLICK_ADD);
                break;
            case 1:
                startActivity(new Intent(this, NoteCreateActivity.class));
                break;
        }
    }

    private List<String> initTitles() {
        List<String> titleList = new ArrayList<>();
        titleList.add(ResUtils.getString(R.string.tab_main_pswd));
        titleList.add(ResUtils.getString(R.string.tab_main_note));
        titleList.add(ResUtils.getString(R.string.tab_main_pay));
        return titleList;
    }

    private List<Fragment> initFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(PswdFragment.getInstances());
        fragmentList.add(new NoteFragment());
        fragmentList.add(new PayFragment());
        return fragmentList;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.search) {
            switch (viewPager.getCurrentItem()) {
                case 0:
                    presenter.onUIClick(CLICK_SEARCH);
                    break;
            }
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPswdFuncLock(final int clickType, PassConfig passConfig) {
        int requestCode = REQUEST_UNLOCK_FOR_ADD;
        switch (clickType) {
            case CLICK_ADD:
                requestCode = REQUEST_UNLOCK_FOR_ADD;
                break;
            case CLICK_SEARCH:
                requestCode = REQUEST_UNLOCK_FOR_SEARCH;
                break;
        }
        if (passConfig.isFingerPrintAgreed()) {
            FingerprintHelper.getInstances().validate(this, new OnFingerPrintAuthListener() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {

                }

                @Override
                public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    App.setIsPswdFunctionLocked(false);
                    if (clickType == CLICK_ADD) {
                        Intent intent = new Intent(MainActivity.this, DefaultCatActivity.class);
                        startActivityForResult(intent, REQUEST_CREATE_PASSWORD);
                    } else if (clickType == CLICK_SEARCH) {
                        startActivity(new Intent(MainActivity.this, PassSearchActivity.class));
                    }
                }

                @Override
                public void onAuthenticationFailed() {

                }

                @Override
                public void onAuthDialogCancel() {

                }
            });
        } else {
            //已经锁定
            switch (passConfig.getPreferLockType()) {
                case PassConfig.LockType.GRID:
                    //网格密码解锁
                    Intent intent = new Intent(this, GridUnlockActivity.class);
                    startActivityForResult(intent, requestCode);
                    break;
                case PassConfig.LockType.TEXT:
                    Intent textUnlock = new Intent(this, TextUnlockActivity.class);
                    startActivityForResult(textUnlock, requestCode);
                    break;
                case PassConfig.LockType.PATTERN:
                    Intent patternUnlock = new Intent(this, PatternUnlockActivity.class);
                    startActivityForResult(patternUnlock, requestCode);
                    break;
            }
        }
    }

    @Override
    public void onPswdFuncUnlock(int clickType, PassConfig passConfig) {
        //未被锁定
        if (clickType == CLICK_ADD) {
            Intent intent = new Intent(this, DefaultCatActivity.class);
            startActivity(intent);
        } else if (clickType == CLICK_SEARCH) {
            Intent intent = new Intent(this, PassSearchActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onMasterNotSetBefore(final int clickType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_error_red_24dp);
        builder.setTitle(R.string.title_dialog_tips);
        builder.setMessage(R.string.msg_dialog_master_not_set);
        builder.setPositiveButton(R.string.action_dialog_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, MasterTypeActivity.class);
                if (clickType == CLICK_ADD) {
                    startActivityForResult(intent, REQUEST_CREATE_MASTER_FOR_ADD);
                } else if (clickType == CLICK_SEARCH) {
                    startActivityForResult(intent, REQUEST_CREATE_MASTER_FOR_SEARCH);
                }
            }
        });
        builder.setNegativeButton(R.string.action_dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public void onDataTamper() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_error_red_24dp);
        builder.setTitle(R.string.title_dialog_error);
        builder.setMessage(R.string.error_data_tamper);
        builder.setPositiveButton(R.string.action_dialog_getit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public void onPswdClickError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CREATE_MASTER_FOR_ADD:
                    startActivityForResult(new Intent(this, DefaultCatActivity.class), REQUEST_CREATE_PASSWORD);
                    break;
                case REQUEST_CREATE_MASTER_FOR_SEARCH:
                    startActivity(new Intent(this, PassSearchActivity.class));
                    break;
                case REQUEST_UNLOCK_FOR_ADD:
                    startActivityForResult(new Intent(this, DefaultCatActivity.class), REQUEST_CREATE_PASSWORD);
                    break;
                case REQUEST_UNLOCK_FOR_SEARCH:
                    startActivity(new Intent(this, PassSearchActivity.class));
                    break;
                case REQUEST_CREATE_PASSWORD:
                    break;
            }
        }
    }

}

package com.slut.simrec;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.slut.simrec.database.DBHelper;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.database.pswd.dao.PassDao;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class App extends Application {

    private static Context context;
    private static DBHelper dbHelper;
    private static boolean isPswdFunctionLocked = true;

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(getApplicationContext());
        setDbHelper();
        initDaoes();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        App.context = context;
    }

    public static boolean isPswdFunctionLocked() {
        return isPswdFunctionLocked;
    }

    public static void setIsPswdFunctionLocked(boolean isPswdFunctionLocked) {
        App.isPswdFunctionLocked = isPswdFunctionLocked;
    }

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    public static void setDbHelper() {
        App.dbHelper = DBHelper.getHelper();
    }

    private void initDaoes() {
        PassConfigDao.getInstances().init();
        PassDao.getInstances().init();
    }

}

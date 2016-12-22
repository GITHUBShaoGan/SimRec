package com.slut.simrec;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.slut.simrec.database.DBHelper;
import com.slut.simrec.database.note.dao.NoteLabelDao;
import com.slut.simrec.database.pswd.dao.PassCatDao;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.database.pswd.dao.PassDao;
import com.slut.simrec.utils.FileUtils;

import java.util.Stack;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class App extends Application {

    private static Context context;
    private static DBHelper dbHelper;
    private static boolean isPswdFunctionLocked = true;
    private static int activityCount = 0;
    private Stack<Activity> activities = new Stack<>();
    private static volatile App instances;

    public static App getInstances() {
        if(instances == null){
            synchronized (App.class){
                if(instances == null){
                    instances = new App();
                }
            }
        }
        return instances;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(getApplicationContext());
        setDbHelper();
        initDaoes();
        initUniversalImageLoader();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                if (activityCount == 0) {
                    setIsPswdFunctionLocked(true);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initUniversalImageLoader() {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)//璁剧疆绾跨▼浼樺厛绾?
                .threadPoolSize(4)//绾跨▼姹犲唴鍔犺浇鐨勬暟閲?鎺ㄨ崘鑼冨洿1-5鍐呫€?
                .denyCacheImageMultipleSizesInMemory()//褰撳悓涓€涓猆ri鑾峰彇涓嶅悓澶у皬鐨勫浘鐗囩紦瀛樺埌鍐呭瓨涓椂鍙紦瀛樹竴涓€備笉璁剧疆鐨勮瘽榛樿浼氱紦瀛樺涓笉鍚屽ぇ灏忕殑鍥剧墖
                .memoryCacheExtraOptions(480, 800)//鍐呭瓨缂撳瓨鏂囦欢鐨勬渶澶ч暱搴?
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))//鍐呭瓨缂撳瓨鏂瑰紡,杩欓噷鍙互鎹㈡垚鑷繁鐨勫唴瀛樼紦瀛樺疄鐜般€?鎺ㄨ崘LruMemoryCache,閬撶悊鑷繁鎳傜殑)
                .memoryCacheSize(10 * 1024 * 1024)//鍐呭瓨缂撳瓨鐨勬渶澶у€?
                .diskCache(new UnlimitedDiskCache(FileUtils.createImageCacheSavePath()))//鍙互鑷畾涔夌紦瀛樿矾寰?
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//瀵逛繚瀛樼殑URL杩涜鍔犲瘑淇濆瓨
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000))//璁剧疆杩炴帴鏃堕棿5s,瓒呮椂鏃堕棿30s
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);
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
        PassCatDao.getInstances().init();
        NoteLabelDao.getInstances().init();
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }

    public void exit() {
        if (activities != null) {
            for (Activity activity : activities) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        System.exit(0);
    }

}

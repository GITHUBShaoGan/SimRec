package com.slut.simrec.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.slut.simrec.R;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class ImgLoaderOptions {

    public static DisplayImageOptions init404Options(){
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.cacheInMemory(true);
        builder.cacheOnDisk(true);
        builder.showImageForEmptyUri(R.drawable.error);
        builder.showImageOnFail(R.drawable.error);
        builder.bitmapConfig(Bitmap.Config.RGB_565);
        return builder.build();
    }

}

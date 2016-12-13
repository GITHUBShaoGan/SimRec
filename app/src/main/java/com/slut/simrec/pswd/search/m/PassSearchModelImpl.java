package com.slut.simrec.pswd.search.m;

import android.text.TextUtils;

/**
 * Created by 七月在线科技 on 2016/12/13.
 */

public class PassSearchModelImpl implements PassSearchModel {

    @Override
    public void search(String regex, OnSearchListener onSearchListener) {
        if(TextUtils.isEmpty(regex)){
            onSearchListener.onSearchError("");
            return;
        }
        
    }

}

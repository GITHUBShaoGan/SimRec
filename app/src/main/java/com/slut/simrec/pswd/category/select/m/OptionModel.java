package com.slut.simrec.pswd.category.select.m;

import com.slut.simrec.database.pswd.bean.PassCat;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public interface OptionModel {

    interface OnCreateCatListener {

        void onCreateSuccess(PassCat passCat);

        void onCreateError(String msg);

    }

    void create(String title, String url, String iconUrl, OnCreateCatListener onCreateCatListener);

    interface OnLoadMoreListener {

        void onLoadMoreSuccess(int type, List<PassCat> passCatList);

        void onLoadMoreError(String msg);

    }

    void loadMore(long pageNo, long pageSize, OnLoadMoreListener onLoadMoreListener);

}

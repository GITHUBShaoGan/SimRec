package com.slut.simrec.pswd.defaultcat.m;

import com.slut.simrec.pswd.defaultcat.bean.DefaultCatBean;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public interface DefaultCatModel {

    interface OnDataLoadListener{

        void onDataLoadSuccess(List<DefaultCatBean> defaultCatBeanList);

        void onDataLoadError(String msg);

    }

    void loadData(OnDataLoadListener onDataLoadListener);

}

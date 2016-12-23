package com.slut.simrec.pswd.category.defaultcat.m;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.pswd.category.defaultcat.bean.DefaultCatBean;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public interface DefaultCatModel {

    interface OnDataLoadListener {

        void onDataLoadSuccess(List<DefaultCatBean> defaultCatBeanList);

        void onDataLoadError(String msg);

    }

    void loadData(OnDataLoadListener onDataLoadListener);

    interface OnItemClickListener {

        void onItemClick(boolean isExists,PassCat passCat, int position);

    }

    void insertCat(int position, DefaultCatBean defaultCatBean,DefaultCatModel.OnItemClickListener onItemClickListener);

}

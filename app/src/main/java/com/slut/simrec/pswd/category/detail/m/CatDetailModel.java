package com.slut.simrec.pswd.category.detail.m;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/9.
 */

public interface CatDetailModel {

    interface OnDataLoadListener {

        void onLoadSuccess(int type, List<Password> passwordList);

        void onLoadError(String msg);

    }

    void loadData(PassCat passCat, OnDataLoadListener onDataLoadListener);

    interface OnDeleteListener {

        void onDeleteSuccess(PassCat passCat,int deleteType);

        void onDeleteError(String msg);

    }

    void delete(int deleteType,PassCat passCat, OnDeleteListener onDeleteListener);

    interface OnEditListener {

        void onEditSuccess(PassCat passCat);

        void onEditError(String msg);

    }

    void edit(PassCat passCat,String title,String url,String iconURL,OnEditListener onEditListener);

}

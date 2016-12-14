package com.slut.simrec.pswd.create.m;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.pswd.category.defaultcat.bean.DefaultCatBean;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public interface PswdNewModel {

    interface OnBackOnClickListener {

        void onUIChange();

        void onUINotChange();

    }

    void onBackClick(String title, String account, String password, String website, String remark, PassCat passCat,String originPassUUID, OnBackOnClickListener onBackOnClickListener);

    interface OnSavePswdListener{

        void onPswdSaveSuccess(Password password);

        void onPswdSaveError(String msg);

    }

    void save(String title, String account, String password, String website, String remark,String passCatUUID,OnSavePswdListener onSavePswdListener);

}

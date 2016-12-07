package com.slut.simrec.pswd.create.p;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.pswd.category.defaultcat.bean.DefaultCatBean;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public interface PswdNewPresenter {

    void onBackClick(String title, String account, String password, String website, String remark, PassCat passCat,String originPassUUID);

    void save(String title, String account, String password, String website, String remark,String passCatUUID);

}

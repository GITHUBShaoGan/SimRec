package com.slut.simrec.pswd.detail.p;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;

/**
 * Created by 七月在线科技 on 2016/12/13.
 */

public interface PassDetailPresenter {

    void update(String title, String account, String password, String websiteURL, String remark, Password pswd, PassCat passCat);

}

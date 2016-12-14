package com.slut.simrec.pswd.create.v;

import com.slut.simrec.database.pswd.bean.Password;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public interface PswdNewView {

    void onUIChange();

    void onUINotChange();

    void onPswdSaveSuccess(Password password);

    void onPswdSaveError(String msg);

}

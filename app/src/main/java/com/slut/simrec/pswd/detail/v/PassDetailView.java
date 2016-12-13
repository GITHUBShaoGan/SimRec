package com.slut.simrec.pswd.detail.v;

import com.slut.simrec.database.pswd.bean.Password;

/**
 * Created by 七月在线科技 on 2016/12/13.
 */

public interface PassDetailView {

    void onUpdateSuccess(Password password);

    void onUpdateError(String msg);

}

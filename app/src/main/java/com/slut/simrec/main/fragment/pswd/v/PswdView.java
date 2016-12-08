package com.slut.simrec.main.fragment.pswd.v;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public interface PswdView {

    void onLoadSuccess(int type, List<Password> passwordList);

    void onLoadError(String msg);

    void onPassCatLoadSuccess(int type, List<PassCat> passCatList, List<List<Password>> passwordList);

    void onPassCatLoadError(String msg);

}

package com.slut.simrec.pswd.search.v;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/13.
 */

public interface PassSearchView {

    void onSearchSuccess(List<Password> passwordList, List<PassCat> passCatList);

    void onSearchError(String msg);

}

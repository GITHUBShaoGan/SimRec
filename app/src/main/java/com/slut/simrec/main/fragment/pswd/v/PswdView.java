package com.slut.simrec.main.fragment.pswd.v;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public interface PswdView {

    void onPassCatLoadSuccess(int type, List<PassCat> passCatList, List<List<Password>> passwordList);

    void onPassCatLoadError(String msg);

    void onInsertSingleCatSuccess(List<PassCat> passCatList,List<List<Password>> passwords);

    void onInsertSingleCatError(String msg);

    void onDeleteSingleCatSuccess(List<PassCat> passCatList, List<List<Password>> passwords,int deletePosition);

    void onDeleteSingleCatError(String msg);

}

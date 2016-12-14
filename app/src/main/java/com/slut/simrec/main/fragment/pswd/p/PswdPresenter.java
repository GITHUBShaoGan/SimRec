package com.slut.simrec.main.fragment.pswd.p;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public interface PswdPresenter {

    void loadPassCat(long pageNo, long pageSize);

    void insertSingleCat(PassCat passCat, List<PassCat> passCatList,List<List<Password>> passwords);

    void deleteSingleCat(PassCat passCat, int deleteType, List<PassCat> passCatList, List<List<Password>> passwords);
}

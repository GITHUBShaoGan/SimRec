package com.slut.simrec.pswd.category.select.v;

import com.slut.simrec.database.pswd.bean.PassCat;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public interface OptionView {

    void onCreateSuccess(PassCat passCat);

    void onCreateError(String msg);

    void onLoadMoreSuccess(int type, List<PassCat> passCatList);

    void onLoadMoreError(String msg);

}

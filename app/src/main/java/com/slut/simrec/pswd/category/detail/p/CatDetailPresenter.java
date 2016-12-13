package com.slut.simrec.pswd.category.detail.p;

import com.slut.simrec.database.pswd.bean.PassCat;

/**
 * Created by 七月在线科技 on 2016/12/9.
 */

public interface CatDetailPresenter {

    void loadData(PassCat passCat);

    void delete(int deleteType,PassCat passCat);

    void edit(PassCat passCat,String title,String url,String iconURL);

}

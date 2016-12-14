package com.slut.simrec.pswd.category.detail.v;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/9.
 */

public interface CatDetailView {

    void onLoadSuccess(int type, List<Password> passwordList);

    void onLoadError(String msg);

    void onDeleteSuccess(PassCat passCat,int deleteType);

    void onDeleteError(String msg);

    void onEditSuccess(PassCat passCat);

    void onEditError(String msg);

}

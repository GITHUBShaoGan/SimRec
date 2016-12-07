package com.slut.simrec.pswd.defaultcat.v;

import com.slut.simrec.pswd.defaultcat.bean.DefaultCatBean;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public interface DefaultCatView {

    void onDataLoadSuccess(List<DefaultCatBean> defaultCatBeanList);

    void onDataLoadError(String msg);

}

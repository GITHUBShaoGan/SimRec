package com.slut.simrec.pswd.defaultcat.m;

import com.slut.simrec.R;
import com.slut.simrec.pswd.defaultcat.bean.DefaultCatBean;
import com.slut.simrec.utils.ResUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class DefaultCatModelImpl implements DefaultCatModel {
    @Override
    public void loadData(OnDataLoadListener onDataLoadListener) {
        String[] websiteArr = ResUtils.getStringArray(R.array.url_website);
        String[] titleArr = ResUtils.getStringArray(R.array.title_website);
        String[] avatarArr = ResUtils.getStringArray(R.array.icon_website);
        if (websiteArr != null && titleArr != null && avatarArr != null && websiteArr.length == titleArr.length && websiteArr.length == websiteArr.length) {
            List<DefaultCatBean> defaultCatBeanList = new ArrayList<>();
            for (int i = 0; i < websiteArr.length; i++) {
                DefaultCatBean defaultCatBean = new DefaultCatBean(titleArr[i], websiteArr[i], avatarArr[i]);
                defaultCatBeanList.add(defaultCatBean);
            }
            onDataLoadListener.onDataLoadSuccess(defaultCatBeanList);
        } else {
            onDataLoadListener.onDataLoadError(ResUtils.getString(R.string.error_exception_happened));
        }
    }
}

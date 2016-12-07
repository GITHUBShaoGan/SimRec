package com.slut.simrec.pswd.category.defaultcat.m;

import android.text.TextUtils;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.dao.PassCatDao;
import com.slut.simrec.pswd.category.defaultcat.bean.DefaultCatBean;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    public void insertCat(int position, DefaultCatBean defaultCatBean, OnItemClickListener onItemClickListener) {
        if (defaultCatBean == null) {
            return;
        }
        String title = RSAUtils.encrypt(defaultCatBean.getTitle());
        String url = RSAUtils.encrypt(defaultCatBean.getWebsite());
        String iconUrl = RSAUtils.encrypt(defaultCatBean.getIconUrl());
        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();
        PassCat passCat = new PassCat(uuid, title, url, iconUrl, stamp, stamp);
        List<PassCat> passCatList = null;
        try {
            passCatList = PassCatDao.getInstances().queryByTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (passCatList != null && !passCatList.isEmpty()) {
            //存在
            onItemClickListener.onItemClick(passCatList.get(0), position);
        } else {
            //不存在
            try {
                //管他娘的存不存在，删了再说
                PassCatDao.getInstances().deleteByTitle(title);
                PassCatDao.getInstances().insertSingle(passCat);
                onItemClickListener.onItemClick(passCat, position);
            } catch (Exception e) {

            }
        }
    }
}

package com.slut.simrec.pswd.category.select.m;

import android.text.TextUtils;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.dao.PassCatDao;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class  OptionModelImpl implements OptionModel {

    @Override
    public void create(String title, String url, String iconUrl, OnCreateCatListener onCreateCatListener) {
        if (TextUtils.isEmpty(title)) {
            onCreateCatListener.onCreateError(ResUtils.getString(R.string.error_category_options_empty_title));
            return;
        }
        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();
        String newTitle = RSAUtils.encrypt(title);
        String newUrl = RSAUtils.encrypt(url);
        String newIconUrl = RSAUtils.encrypt(iconUrl);
        PassCat passCat = new PassCat(uuid, newTitle, newUrl, newIconUrl,true, stamp, stamp);
        List<PassCat> passCatList = null;
        try {
            passCatList = PassCatDao.getInstances().queryByTitle(newTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (passCatList != null && !passCatList.isEmpty()) {
            //存在
            onCreateCatListener.onCreateError(ResUtils.getString(R.string.error_category_already_exists));
        } else {
            //不存在
            try {
                //管他娘的存不存在，删了再说
                PassCatDao.getInstances().deleteByTitle(title);
                PassCatDao.getInstances().insertSingle(passCat);
                onCreateCatListener.onCreateSuccess(passCat);
            } catch (Exception e) {
                if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                    onCreateCatListener.onCreateError(e.getLocalizedMessage());
                } else {
                    onCreateCatListener.onCreateError(ResUtils.getString(R.string.error_exception_happened));
                }
            }
        }
    }

    @Override
    public void loadMore(long pageNo, long pageSize, OnLoadMoreListener onLoadMoreListener) {
        if (pageNo < 1 || pageSize < 1) {
            onLoadMoreListener.onLoadMoreError(ResUtils.getString(R.string.error_illegal_request));
            return;
        }
        try {
            List<PassCat> passCatList = PassCatDao.getInstances().queryByPage(pageNo, pageSize);
            if (passCatList != null) {
                if (passCatList.size() < pageSize) {
                    onLoadMoreListener.onLoadMoreSuccess(LoadMoreType.TYPE_END, passCatList);
                } else if (passCatList.size() == pageSize) {
                    onLoadMoreListener.onLoadMoreSuccess(LoadMoreType.TYPE_COMPLETE, passCatList);
                } else {
                    onLoadMoreListener.onLoadMoreError(ResUtils.getString(R.string.error_size));
                }
            } else {
                onLoadMoreListener.onLoadMoreError(ResUtils.getString(R.string.error_query_failed));
            }
        } catch (Exception e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onLoadMoreListener.onLoadMoreError(e.getLocalizedMessage());
            } else {
                onLoadMoreListener.onLoadMoreError(ResUtils.getString(R.string.error_exception_happened));
            }
        }
    }

}

package com.slut.simrec.main.m;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.database.pswd.dao.PassDao;
import com.slut.simrec.utils.ResUtils;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class MainModelImpl implements MainModel {

    @Override
    public void onUIClick(int clickType,OnUIClickListener onUIClickListener) {
        try {
            List<PassConfig> passConfigList = PassConfigDao.getInstances().queryAll();
            if (passConfigList != null) {
                if (passConfigList.isEmpty()) {
                    //主密码尚未设置
                    onUIClickListener.onMasterNotSetBefore(clickType);
                } else if (passConfigList.size() == 1) {
                    //用户密码已经设置了
                    if (App.isPswdFunctionLocked()) {
                        //密码功能被锁定
                        onUIClickListener.onPswdFuncLock(clickType,passConfigList.get(0));
                    } else {
                        //密码功能没被锁定
                        onUIClickListener.onPswdFuncUnlock(clickType,passConfigList.get(0));
                    }
                } else {
                    //数据有可能被篡改
                    PassConfigDao.getInstances().deleteAll();
                    PassDao.getInstances().deleteAll();
                    onUIClickListener.onDataTamper();
                }
            } else {
                onUIClickListener.onClickError(ResUtils.getString(R.string.error_exception_happened));
            }
        } catch (Exception e) {
            onUIClickListener.onClickError(e.getLocalizedMessage());
        }
    }

}

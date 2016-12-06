package com.slut.simrec.database.pswd.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */
@DatabaseTable
public class Password {

    @DatabaseField(id = true)
    private String uuid;
    @DatabaseField
    private String title;
    @DatabaseField
    private String account;
    @DatabaseField
    private String password;
    @DatabaseField
    private String websiteUrl;
    @DatabaseField
    private String remark;
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public Password() {
    }
}

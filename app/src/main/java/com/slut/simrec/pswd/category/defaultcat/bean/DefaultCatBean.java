package com.slut.simrec.pswd.category.defaultcat.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class DefaultCatBean implements Parcelable{

    private String title;

    private String website;

    private String iconUrl;

    public DefaultCatBean(String title, String website, String iconUrl) {
        this.title = title;
        this.website = website;
        this.iconUrl = iconUrl;
    }

    protected DefaultCatBean(Parcel in) {
        title = in.readString();
        website = in.readString();
        iconUrl = in.readString();
    }

    public static final Creator<DefaultCatBean> CREATOR = new Creator<DefaultCatBean>() {
        @Override
        public DefaultCatBean createFromParcel(Parcel in) {
            return new DefaultCatBean(in);
        }

        @Override
        public DefaultCatBean[] newArray(int size) {
            return new DefaultCatBean[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(website);
        parcel.writeString(iconUrl);
    }
}

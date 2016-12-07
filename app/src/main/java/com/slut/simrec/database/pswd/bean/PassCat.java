package com.slut.simrec.database.pswd.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */
@DatabaseTable
public class PassCat implements Parcelable{

    @DatabaseField
    private String uuid;
    @DatabaseField
    private String catTitle;
    @DatabaseField
    private String catUrl;
    @DatabaseField
    private String catIconUrl;
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public PassCat() {
    }

    public PassCat(String uuid, String catTitle, String catUrl, String catIconUrl, long createStamp, long updateStamp) {
        this.uuid = uuid;
        this.catTitle = catTitle;
        this.catUrl = catUrl;
        this.catIconUrl = catIconUrl;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    protected PassCat(Parcel in) {
        uuid = in.readString();
        catTitle = in.readString();
        catUrl = in.readString();
        catIconUrl = in.readString();
        createStamp = in.readLong();
        updateStamp = in.readLong();
    }

    public static final Creator<PassCat> CREATOR = new Creator<PassCat>() {
        @Override
        public PassCat createFromParcel(Parcel in) {
            return new PassCat(in);
        }

        @Override
        public PassCat[] newArray(int size) {
            return new PassCat[size];
        }
    };

    @Override
    public String toString() {
        return "PassCat{" +
                "uuid='" + uuid + '\'' +
                ", catTitle='" + catTitle + '\'' +
                ", catUrl='" + catUrl + '\'' +
                ", catIconUrl='" + catIconUrl + '\'' +
                ", createStamp=" + createStamp +
                ", updateStamp=" + updateStamp +
                '}';
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCatTitle() {
        return catTitle;
    }

    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }

    public String getCatUrl() {
        return catUrl;
    }

    public void setCatUrl(String catUrl) {
        this.catUrl = catUrl;
    }

    public String getCatIconUrl() {
        return catIconUrl;
    }

    public void setCatIconUrl(String catIconUrl) {
        this.catIconUrl = catIconUrl;
    }

    public long getCreateStamp() {
        return createStamp;
    }

    public void setCreateStamp(long createStamp) {
        this.createStamp = createStamp;
    }

    public long getUpdateStamp() {
        return updateStamp;
    }

    public void setUpdateStamp(long updateStamp) {
        this.updateStamp = updateStamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(catTitle);
        parcel.writeString(catUrl);
        parcel.writeString(catIconUrl);
        parcel.writeLong(createStamp);
        parcel.writeLong(updateStamp);
    }
}

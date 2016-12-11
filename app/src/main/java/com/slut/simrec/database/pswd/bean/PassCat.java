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
    private boolean isExpand;
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public PassCat() {
    }

    public PassCat(String uuid, String catTitle, String catUrl, String catIconUrl, boolean isExpand, long createStamp, long updateStamp) {
        this.uuid = uuid;
        this.catTitle = catTitle;
        this.catUrl = catUrl;
        this.catIconUrl = catIconUrl;
        this.isExpand = isExpand;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    protected PassCat(Parcel in) {
        uuid = in.readString();
        catTitle = in.readString();
        catUrl = in.readString();
        catIconUrl = in.readString();
        isExpand = in.readByte() != 0;
        createStamp = in.readLong();
        updateStamp = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(catTitle);
        dest.writeString(catUrl);
        dest.writeString(catIconUrl);
        dest.writeByte((byte) (isExpand ? 1 : 0));
        dest.writeLong(createStamp);
        dest.writeLong(updateStamp);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
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
    public String toString() {
        return "PassCat{" +
                "uuid='" + uuid + '\'' +
                ", catTitle='" + catTitle + '\'' +
                ", catUrl='" + catUrl + '\'' +
                ", catIconUrl='" + catIconUrl + '\'' +
                ", isExpand=" + isExpand +
                ", createStamp=" + createStamp +
                ", updateStamp=" + updateStamp +
                '}';
    }
}

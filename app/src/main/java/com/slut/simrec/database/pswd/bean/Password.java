package com.slut.simrec.database.pswd.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */
@DatabaseTable
public class Password implements Parcelable{

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
    private String passCatUUID;
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public Password() {
    }

    public Password(String uuid, String title, String account, String password, String websiteUrl, String remark, String passCatUUID, long createStamp, long updateStamp) {
        this.uuid = uuid;
        this.title = title;
        this.account = account;
        this.password = password;
        this.websiteUrl = websiteUrl;
        this.remark = remark;
        this.passCatUUID = passCatUUID;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    protected Password(Parcel in) {
        uuid = in.readString();
        title = in.readString();
        account = in.readString();
        password = in.readString();
        websiteUrl = in.readString();
        remark = in.readString();
        passCatUUID = in.readString();
        createStamp = in.readLong();
        updateStamp = in.readLong();
    }

    public static final Creator<Password> CREATOR = new Creator<Password>() {
        @Override
        public Password createFromParcel(Parcel in) {
            return new Password(in);
        }

        @Override
        public Password[] newArray(int size) {
            return new Password[size];
        }
    };

    @Override
    public String toString() {
        return "Password{" +
                "uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", remark='" + remark + '\'' +
                ", passCatUUID='" + passCatUUID + '\'' +
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPassCatUUID() {
        return passCatUUID;
    }

    public void setPassCatUUID(String passCatUUID) {
        this.passCatUUID = passCatUUID;
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
        parcel.writeString(title);
        parcel.writeString(account);
        parcel.writeString(password);
        parcel.writeString(websiteUrl);
        parcel.writeString(remark);
        parcel.writeString(passCatUUID);
        parcel.writeLong(createStamp);
        parcel.writeLong(updateStamp);
    }
}

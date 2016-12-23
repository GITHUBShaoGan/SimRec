package com.slut.simrec.database.note.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */
@DatabaseTable
public class Note implements Parcelable{

    @DatabaseField(id = true)
    private String uuid;
    @DatabaseField
    private String title;
    @DatabaseField
    private String content;
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public Note() {
    }

    public Note(String uuid, String title, String content, long createStamp, long updateStamp) {
        this.uuid = uuid;
        this.title = title;
        this.content = content;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    protected Note(Parcel in) {
        uuid = in.readString();
        title = in.readString();
        content = in.readString();
        createStamp = in.readLong();
        updateStamp = in.readLong();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "Note{" +
                "uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createStamp=" + createStamp +
                ", updateStamp=" + updateStamp +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeLong(createStamp);
        parcel.writeLong(updateStamp);
    }
}

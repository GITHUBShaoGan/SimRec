package com.slut.simrec.database.note.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */
@DatabaseTable
public class NoteLabel implements Parcelable{

    @DatabaseField(id = true)
    private String uuid;
    @DatabaseField
    private String name;
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public NoteLabel() {
    }

    public NoteLabel(String uuid, String name, long createStamp, long updateStamp) {
        this.uuid = uuid;
        this.name = name;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    protected NoteLabel(Parcel in) {
        uuid = in.readString();
        name = in.readString();
        createStamp = in.readLong();
        updateStamp = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(name);
        dest.writeLong(createStamp);
        dest.writeLong(updateStamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteLabel> CREATOR = new Creator<NoteLabel>() {
        @Override
        public NoteLabel createFromParcel(Parcel in) {
            return new NoteLabel(in);
        }

        @Override
        public NoteLabel[] newArray(int size) {
            return new NoteLabel[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "NoteLabel{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", createStamp=" + createStamp +
                ", updateStamp=" + updateStamp +
                '}';
    }
}

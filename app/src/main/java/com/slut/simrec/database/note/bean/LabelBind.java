package com.slut.simrec.database.note.bean;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class LabelBind implements Parcelable{

    @DatabaseField(id = true)
    private String uuid;
    @DatabaseField
    private String noteUUID;
    @DatabaseField
    private String labelUUID;

    public LabelBind() {
    }

    public LabelBind(String uuid, String noteUUID, String labelUUID) {
        this.uuid = uuid;
        this.noteUUID = noteUUID;
        this.labelUUID = labelUUID;
    }

    protected LabelBind(Parcel in) {
        uuid = in.readString();
        noteUUID = in.readString();
        labelUUID = in.readString();
    }

    public static final Creator<LabelBind> CREATOR = new Creator<LabelBind>() {
        @Override
        public LabelBind createFromParcel(Parcel in) {
            return new LabelBind(in);
        }

        @Override
        public LabelBind[] newArray(int size) {
            return new LabelBind[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNoteUUID() {
        return noteUUID;
    }

    public void setNoteUUID(String noteUUID) {
        this.noteUUID = noteUUID;
    }

    public String getLabelUUID() {
        return labelUUID;
    }

    public void setLabelUUID(String labelUUID) {
        this.labelUUID = labelUUID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(noteUUID);
        parcel.writeString(labelUUID);
    }
}

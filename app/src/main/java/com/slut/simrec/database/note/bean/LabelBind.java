package com.slut.simrec.database.note.bean;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class LabelBind {

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
}

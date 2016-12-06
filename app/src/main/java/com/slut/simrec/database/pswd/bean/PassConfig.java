package com.slut.simrec.database.pswd.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */
@DatabaseTable
public class PassConfig {

    public class LockType {
        public static final int NOT_SET = -1;
        public static final int GRID = 1;
        public static final int PATTERN = 2;
        public static final int TEXT = 3;
    }

    @DatabaseField(id = true)
    private String uuid;
    @DatabaseField
    private String gridPass;
    @DatabaseField
    private String patternPass;
    @DatabaseField
    private String textPass;
    @DatabaseField
    private boolean isFingerPrintAgreed;
    @DatabaseField
    private int preferLockType;//用户设置的解锁类型，-1代表尚未设置密码，1代表网格密码，2代表手势密码，3代表文字密码
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public PassConfig() {
    }

    public PassConfig(String uuid, String gridPass, String patternPass, String textPass, boolean isFingerPrintAgreed, int preferLockType, long createStamp, long updateStamp) {
        this.uuid = uuid;
        this.gridPass = gridPass;
        this.patternPass = patternPass;
        this.textPass = textPass;
        this.isFingerPrintAgreed = isFingerPrintAgreed;
        this.preferLockType = preferLockType;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    @Override
    public String toString() {
        return "PassConfig{" +
                "uuid='" + uuid + '\'' +
                ", gridPass='" + gridPass + '\'' +
                ", patternPass='" + patternPass + '\'' +
                ", textPass='" + textPass + '\'' +
                ", isFingerPrintAgreed=" + isFingerPrintAgreed +
                ", preferLockType=" + preferLockType +
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

    public String getGridPass() {
        return gridPass;
    }

    public void setGridPass(String gridPass) {
        this.gridPass = gridPass;
    }

    public String getPatternPass() {
        return patternPass;
    }

    public void setPatternPass(String patternPass) {
        this.patternPass = patternPass;
    }

    public String getTextPass() {
        return textPass;
    }

    public void setTextPass(String textPass) {
        this.textPass = textPass;
    }

    public boolean isFingerPrintAgreed() {
        return isFingerPrintAgreed;
    }

    public void setFingerPrintAgreed(boolean fingerPrintAgreed) {
        isFingerPrintAgreed = fingerPrintAgreed;
    }

    public int getPreferLockType() {
        return preferLockType;
    }

    public void setPreferLockType(int preferLockType) {
        this.preferLockType = preferLockType;
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
}

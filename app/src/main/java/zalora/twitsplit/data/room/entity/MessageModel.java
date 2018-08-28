package zalora.twitsplit.data.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import zalora.twitsplit.utils.CommonUtils;

@Entity(tableName = "message_model")
public class MessageModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "_message")
    private String message;

    @ColumnInfo(name = "_created")
    private long created;

    @ColumnInfo(name = "_group_key")
    private String groupKey;

    public MessageModel(String message, long created, String groupKey) {
        this.message = message;
        this.created = created;
        this.groupKey = groupKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreated() {
        return created;
    }

    public String getLongStringCreated() {
        Date d = new Date(created);
        return CommonUtils.getLongStringFrom(d);
    }

    public String getShortStringCreated() {
        Date d = new Date(created);
        return CommonUtils.getShortStringFrom(d);
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
}

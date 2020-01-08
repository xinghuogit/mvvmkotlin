package com.spark.mvvmjava.bean;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/*************************************************************************************************
 * 日期：2020/1/8 16:52
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/

/**
 * 在删除和更新的时候用了onDelete = CASCADE,onUpdate = CASCADE。这里动作有以下：
 * <p>
 * NO_ACTION：当person中的uuid有变化的时候clothes的ownerUuid不做任何动作
 * RESTRICT：当person中的uuid在clothes里有依赖的时候禁止对person做动作，做动作就会报错。
 * SET_NULL：当person中的uuid有变化的时候clothes的ownerUuid会设置为NULL。
 * SET_DEFAULT：当person中的uuid有变化的时候clothes的ownerUuid会设置为默认值，我这里是int型，那么会设置为0
 * CASCADE：当person中的uuid有变化的时候clothes的ownerUuid跟着变化，假如我把uuid = 1的数据删除，那么clothes表里，ownerUuid = 1的都会被删除。
 */
@Entity(foreignKeys = @ForeignKey(onDelete = CASCADE, onUpdate = CASCADE, entity = User.class, parentColumns = "uuid", childColumns = "ownerUuid")
        ,indices = {@Index(value = "id",unique = true),@Index(value = "ownerUuid")})
public class Book {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String color;
    private long ownerUuid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getOwnerUuid() {
        return ownerUuid;
    }

    public void setOwnerUuid(long ownerUuid) {
        this.ownerUuid = ownerUuid;
    }
}

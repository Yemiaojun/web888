// TagPojo.java
package com.tr.web111.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName(value = "tag")
public class TagPojo {

    @TableId(value = "pid", type = IdType.AUTO)
    private int pid;

    @TableField(value = "type")
    private int type;

    @TableField(value = "cateID")
    private int cateID;

    @TableField(value = "level")
    private int level;

    @TableField(value = "exp")
    private int exp;

    @TableField(value = "finish")
    private boolean finish;

    @TableField(value = "editTime")
    private Date editTime;

    @TableField(value = "posID")
    private int posID;

    @TableField(value = "did")
    private int did;

    public TagPojo(int pid, int type, int cateID, int level, int exp, boolean finish, Date editTime, int posID, int did) {
        this.pid = pid;
        this.type = type;
        this.cateID = cateID;
        this.level = level;
        this.exp = exp;
        this.finish = finish;
        this.editTime = editTime;
        this.posID = posID;
        this.did = did;
    }
}

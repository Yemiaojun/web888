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
    private Integer pid;

    @TableField(value = "type")
    private Integer type;

    @TableField(value = "cateID")
    private Integer cateID;

    @TableField(value = "level")
    private Integer level;

    @TableField(value = "exp")
    private Integer exp;

    @TableField(value = "finish")
    private Boolean finish;

    @TableField(value = "editTime")
    private Date editTime;

    @TableField(value = "posID")
    private Integer posID;

    @TableField(value = "did")
    private Integer did;

    @TableField(value = "cid")
    private Integer cid;


    public TagPojo(Integer pid, Integer type, Integer cateID, Integer level, Integer exp, Boolean finish, Date editTime, Integer posID, Integer did, Integer cid) {
        this.pid = pid;
        this.type = type;
        this.cateID = cateID;
        this.level = level;
        this.exp = exp;
        this.finish = finish;
        this.editTime = editTime;
        this.posID = posID;
        this.did = did;
        this.cid = cid;
    }
}

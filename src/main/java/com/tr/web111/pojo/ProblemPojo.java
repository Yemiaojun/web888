// ProblemPojo.java
package com.tr.web111.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "problem")
public class ProblemPojo {

    @TableId(value = "pid", type = IdType.AUTO)
    private int pid;

    @TableField(value = "uid")
    private int uid;

    @TableField(value = "note")
    private String note;

    @TableField(value = "code")
    private String code;

    @TableField(value = "title")
    private String title;

    @TableField(value = "description")
    private String description;

    @TableField(value = "addTime")
    private Date addTime; // 新增的addTime字段

    public ProblemPojo(String title, String description, String note, String code, int uid) {
        this.title = title;
        this.description = description;
        this.note = note;
        this.code = code;
        this.uid = uid;
        this.addTime = new Date();
    }
}

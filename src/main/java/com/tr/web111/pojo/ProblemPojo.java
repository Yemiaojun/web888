// ProblemPojo.java
package com.tr.web111.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public ProblemPojo(int uid, String note, String code) {
        this.uid = uid;
        this.note = note;
        this.code = code;
    }
}

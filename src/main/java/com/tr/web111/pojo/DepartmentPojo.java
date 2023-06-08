// DepartmentPojo.java
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
@TableName(value = "department")
public class DepartmentPojo {

    @TableId(value = "did", type = IdType.AUTO)
    private int did;

    @TableField(value = "cid")
    private int cid;

    @TableField(value = "dname")
    private String dname;

    @TableField(value = "uid")
    private int uid;

    public DepartmentPojo(int cid, String dname, int uid) {
        this.cid = cid;
        this.dname = dname;
        this.uid = uid;
    }
}

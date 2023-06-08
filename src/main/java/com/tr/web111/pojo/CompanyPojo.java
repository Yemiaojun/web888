// CompanyPojo.java
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
@TableName(value = "company")
public class CompanyPojo {

    @TableId(value = "cid", type = IdType.AUTO)
    private int cid;

    @TableField(value = "cname")
    private String cname;

    @TableField(value = "uid")
    private int uid;

    public CompanyPojo(String cname, int uid) {
        this.cname = cname;
        this.uid = uid;
    }
}

// CategoryPojo.java
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
@TableName(value = "category")
public class CategoryPojo {

    @TableId(value = "cateID", type = IdType.AUTO)
    private int cateID;

    @TableField(value = "cateName")
    private String cateName;

    @TableField(value = "uid")
    private int uid;

    public CategoryPojo(String cateName, int uid) {
        this.cateName = cateName;
        this.uid = uid;
    }
}

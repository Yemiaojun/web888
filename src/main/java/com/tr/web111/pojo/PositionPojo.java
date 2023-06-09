// PositionPojo.java
package com.tr.web111.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "position")
public class PositionPojo {

    @TableId(value = "posID", type = IdType.AUTO)
    private int posID;

    @TableField(value = "posName")
    private String posName;

    @TableField(value = "uid")
    private int uid;

    public PositionPojo(String posName, int uid) {
        this.posName = posName;
        this.uid = uid;
    }
}

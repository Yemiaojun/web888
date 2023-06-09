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
@TableName(value = "user")
public class UserPojo {

    @TableId(value = "uid",type = IdType.AUTO)
    private int ID;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    public UserPojo(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

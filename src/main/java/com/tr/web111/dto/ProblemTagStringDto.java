package com.tr.web111.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemTagStringDto {

    @ApiModelProperty(value = "题目ID")
    private int pid;

    @ApiModelProperty(value = "用户ID")
    private int uid;

    @ApiModelProperty(value = "题目注释")
    private String note;

    @ApiModelProperty(value = "题目代码")
    private String code;

    @ApiModelProperty(value = "题目标题")
    private String title;

    @ApiModelProperty(value = "题目描述")
    private String description;

    @ApiModelProperty(value = "题目添加时间")
    private Date addTime;

    @ApiModelProperty(value = "标签类型")
    private Integer type;

    @ApiModelProperty(value = "类别ID")
    private String cateID;

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "经验")
    private Integer exp;

    @ApiModelProperty(value = "完成")
    private Boolean finish;

    @ApiModelProperty(value = "编辑时间")
    private String editTime;

    @ApiModelProperty(value = "职位ID")
    private String posID;

    @ApiModelProperty(value = "部门ID")
    private String did;

    @ApiModelProperty(value = "公司ID")
    private String cid;

}
// CategoryController.java
package com.tr.web111.controller;

import com.tr.web111.pojo.CategoryPojo;
import com.tr.web111.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import utils.Result;

@Api(value = "类别的相关接口")
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    // 查找用户的所有类别
    @ApiOperation(value="查找所有类别", notes = "根据uid查找用户所有类别")
    @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true)
    @RequestMapping(value = "/findCategories", method = RequestMethod.GET)
    public String findAllCategoriesByUid(@RequestParam("uid") int uid) {
        List<CategoryPojo> categories = categoryService.findAllCategoriesByUid(uid);
        return Result.okGetStringByData("成功获取类别信息", categories);
    }

    // 根据名称（字符串匹配）查找类别
    @ApiOperation(value="字符串匹配查找类别", notes = "根据uid和cateName相似搜索类别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "cateName", value = "类别名称", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/findCategoriesByName", method = RequestMethod.GET)
    public String findCategoriesByUidAndCateName(@RequestParam("uid") int uid,
                                                 @RequestParam("cateName") String cateName) {
        List<CategoryPojo> categories = categoryService.findCategoriesByUidAndCateName(uid, cateName);
        return Result.okGetStringByData("成功获取类别信息", categories);
    }

    // 修改一个类别
    @ApiOperation(value="修改一个类别", notes = "根据uid和newCateName修改类别名字")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cateId", value = "类别id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "newCateName", value = "新类别名字", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    public String updateCategory(@RequestParam("cateId") int cateId,
                                 @RequestParam("newCateName") String newCateName) {
        categoryService.updateCategory(cateId, newCateName);
        return Result.okGetString("类别信息成功更新");
    }

    // 删除一个类别
    @ApiOperation(value="删除一个类别", notes = "根据cateId删除一个类别")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "cateId", value = "类别id", dataType = "int", paramType = "query", required = true)
    )
    @RequestMapping(value = "/deleteCategory", method = RequestMethod.DELETE)
    public String deleteCategory(@RequestParam("cateId") int cateId) {
        try {
            categoryService.deleteCategory(cateId);
            return Result.okGetString("类别信息成功删除");
        } catch (DataIntegrityViolationException ex) {
            // catch the exception when the foreign key constraint is violated
            return Result.errorGetString("无法删除，因为存在相关联的标签。请先删除或修改这些标签后再试");
        } catch (Exception ex) {
            // catch any other unexpected exceptions
            return Result.errorGetString("删除类别信息时发生错误: " + ex.getMessage());
        }
    }

    // 新建一个类别
    @ApiOperation(value="新建一个类别", notes = "根据uid和cateName新建类别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "cateName", value = "类别名称", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String addCategory(@RequestParam("uid") int uid,
                              @RequestParam("cateName") String cateName) {
        int newCateId = categoryService.addCategory(uid, cateName);
        return Result.okGetStringByData("类别信息成功添加", newCateId);

    }
}

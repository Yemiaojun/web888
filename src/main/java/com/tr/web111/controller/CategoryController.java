// CategoryController.java
package com.tr.web111.controller;

import com.tr.web111.pojo.CategoryPojo;
import com.tr.web111.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import utils.Result;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    // 查找用户的所有类别
    @RequestMapping(value = "/findCategories", method = RequestMethod.GET)
    public String findAllCategoriesByUid(@RequestParam("uid") int uid) {
        List<CategoryPojo> categories = categoryService.findAllCategoriesByUid(uid);
        return Result.okGetStringByData("成功获取类别信息", categories);
    }

    // 根据名称（字符串匹配）查找类别
    @RequestMapping(value = "/findCategoriesByName", method = RequestMethod.GET)
    public String findCategoriesByUidAndCateName(@RequestParam("uid") int uid,
                                                 @RequestParam("cateName") String cateName) {
        List<CategoryPojo> categories = categoryService.findCategoriesByUidAndCateName(uid, cateName);
        return Result.okGetStringByData("成功获取类别信息", categories);
    }

    // 修改一个类别
    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    public String updateCategory(@RequestParam("cateId") int cateId,
                                 @RequestParam("newCateName") String newCateName) {
        categoryService.updateCategory(cateId, newCateName);
        return Result.okGetString("类别信息成功更新");
    }

    // 删除一个类别
    @RequestMapping(value = "/deleteCategory", method = RequestMethod.DELETE)
    public String deleteCategory(@RequestParam("cateId") int cateId) {
        categoryService.deleteCategory(cateId);
        return Result.okGetString("类别信息成功删除");
    }

    // 新建一个类别
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String addCategory(@RequestParam("uid") int uid,
                              @RequestParam("cateName") String cateName) {
        int newCateId = categoryService.addCategory(uid, cateName);
        return Result.okGetStringByData("类别信息成功添加", newCateId);
    }
}

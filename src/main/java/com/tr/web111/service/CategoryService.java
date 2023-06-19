// CategoryService.java
package com.tr.web111.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tr.web111.dao.CategoryDao;
import com.tr.web111.pojo.CategoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    // 查找用户的所有类别
    public List<CategoryPojo> findAllCategoriesByUid(int uid) {
        return categoryDao.selectList(new QueryWrapper<CategoryPojo>().eq("uid", uid));
    }

    // 根据名称（字符串匹配）查找类别
    public List<CategoryPojo> findCategoriesByUidAndCateName(int uid, String cateName) {
        if(cateName != null){
        return categoryDao.selectList(new QueryWrapper<CategoryPojo>()
                .eq("uid", uid)
                .like("cateName", cateName)); }// 使用like进行模糊匹配
        else{
            return categoryDao.selectList(new QueryWrapper<CategoryPojo>()
                    .eq("uid", uid));}
    }

    // 修改一个类别
    public void updateCategory(int cateId, String newCateName) {
        CategoryPojo category = categoryDao.selectById(cateId);
        if (category != null) {
            category.setCateName(newCateName);
            categoryDao.updateById(category);
        }
    }

    // 删除一个类别
    public void deleteCategory(int cateId) {
        categoryDao.deleteById(cateId);
    }

    // 新建一个类别
    public int addCategory(int uid, String cateName) {
        CategoryPojo newCategory = new CategoryPojo(cateName, uid);
        categoryDao.insert(newCategory);
        return newCategory.getCateID();  // 返回新类别的ID
    }
    public String findCateNameByCateId(int cateID) {
        CategoryPojo category = categoryDao.selectById(cateID);
        return category != null ? category.getCateName() : null;
    }
}

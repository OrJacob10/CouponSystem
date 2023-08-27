package com.example.CouponSystem.service;

import com.example.CouponSystem.beans.Category;
import com.example.CouponSystem.exception.CategoryException;

import java.util.List;

public interface CategoryService {
    Category addCategory(Category category) throws CategoryException;
    Category getCategory(int id) throws CategoryException;
    List<Category> getCategories();
    void updateCategory(int id, Category category) throws CategoryException;
    void deleteCategory(int id) throws CategoryException;
    boolean isCategoryExist(int categoryId);
}

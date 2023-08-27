package com.example.CouponSystem.controller;

import com.example.CouponSystem.beans.Category;
import com.example.CouponSystem.exception.CategoryException;
import com.example.CouponSystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Category addCategory(@RequestBody Category category) throws CategoryException {
        return this.categoryService.addCategory(category);
    }

    @GetMapping
    public List<Category> getCategories(){
        return this.categoryService.getCategories();
    }

    @GetMapping("{id}")
    public Category getCategory(@PathVariable int id) throws CategoryException {
        return this.categoryService.getCategory(id);
    }

    @PutMapping
    public void updateCategory(@PathVariable int id, @RequestBody Category category) throws CategoryException {
        this.categoryService.updateCategory(id,category);
    }

    @DeleteMapping("{id}")
    public void deleteCompany(@PathVariable int id) throws CategoryException {
        this.categoryService.deleteCategory(id);
    }
}

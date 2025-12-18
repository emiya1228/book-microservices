package com.book.bookcore.Controller;

import com.book.bookcommon.result.Response;
import com.book.bookcore.entity.BookCategory;
import com.book.bookcore.service.BookCategoryService;
import com.book.bookcore.vo.CategoryTreeVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/core/category")
public class BookCategoryController {
    @Resource
    private BookCategoryService bookCategoryService;

    /**
     * 获取分类树
     */
    @GetMapping("/tree")
    public Response getCategoryTree() {
        List<CategoryTreeVO> categoryTree = bookCategoryService.getCategoryTree();
        return Response.success(categoryTree);
    }

    /**
     * 获取子分类
     */
    @GetMapping("/{parentId}/children")
    public Response getSubCategories(@PathVariable Long parentId) {
        List<BookCategory> categories = bookCategoryService.getSubCategories(parentId);
        return Response.success(categories);
    }

    /**
     * 启用分类
     */
    @PostMapping("/{id}/enable")
    public Response enableCategory(@PathVariable Long id) {
        bookCategoryService.enableCategory(id);
        return Response.success(null);
    }

    /**
     * 禁用分类
     */
    @PostMapping("/{id}/disable")
    public Response disableCategory(@PathVariable Long id) {
        bookCategoryService.disableCategory(id);
        return Response.success(null);
    }

    /**
     * 创建分类
     */
    @PostMapping
    public Response createCategory(@RequestBody BookCategory category) {
        bookCategoryService.createCategory(category);
        return Response.success(null);
    }

    /**
     * 更新分类
     */
    @PutMapping("/{id}")
    public Response updateCategory(
            @PathVariable Long id,
            @RequestBody BookCategory category) {
        category.setId(id);
        bookCategoryService.updateCategory(category);
        return Response.success(null);
    }
}

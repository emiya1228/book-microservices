package com.book.bookcore.Controller;

import com.book.bookcommon.result.Response;
import com.book.bookcore.entity.BookTag;
import com.book.bookcore.service.BookTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/core/tag")
public class BookTagController {
    @Resource
    private BookTagService bookTagService;

    /**
     * 获取或创建标签
     */
    @PostMapping("/get-or-create")
    public Response getOrCreateTag(
            @RequestParam String name,
            @RequestParam(required = false) String color) {
        BookTag tag = bookTagService.getOrCreateTag(name, color);
        return Response.success(tag);
    }

    /**
     * 获取所有标签
     */
    @GetMapping
    public Response getAllTags() {
        List<BookTag> tags = bookTagService.getAllTags();
        return tags.isEmpty() ? Response.fail(false) : Response.success(tags);
    }


}

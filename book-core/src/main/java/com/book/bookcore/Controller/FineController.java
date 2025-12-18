package com.book.bookcore.Controller;

import com.book.bookcommon.config.UserContext;
import com.book.bookcommon.result.Response;
import com.book.bookcore.entity.Fine;
import com.book.bookcore.service.FineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/core/fine")
public class FineController {
    @Resource
    private FineService fineService;

    @PostMapping("/{id}")
    public Response payFine(@PathVariable Long id) {
        Fine fine = fineService.payFine(id);
        return Response.success(fine);
    }

    @GetMapping("/user")
    public Response getUserFine() {
        Long currentUserId = UserContext.getCurrentUserId();
        List<Fine> fineByUserId = fineService.getFineByUserId(currentUserId);
        return Response.success(fineByUserId);
    }

    @GetMapping("/fineId/{id}")
    public Response getUserFine(@PathVariable Long id) {
        Fine fineByUserId = fineService.getFineById(id);
        return Response.success(fineByUserId);
    }

    @GetMapping("/unpaid")
    public Response getUnpaidUserFine() {
        Long currentUserId = UserContext.getCurrentUserId();
        List<Fine> fineByUserId = fineService.getUnpaidFineByUserId(currentUserId);
        return Response.success(fineByUserId);
    }
}

package com.finpal.web;

import com.finpal.domain.Category;
import com.finpal.repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryRepo categoryRepo;
    @GetMapping public List<Category> list() { return categoryRepo.findAll(); }
}

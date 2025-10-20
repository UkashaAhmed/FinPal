package com.finpal.web;

import com.finpal.service.TipsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai/tips")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TipsController {
    private final TipsService tipsService;
    @GetMapping
    public List<Map<String, String>> tips(@RequestParam(name = "limit", defaultValue = "5") int limit) {
        var all = tipsService.generateBasicTips();
        return all.subList(0, Math.min(limit, all.size()));
    }

}

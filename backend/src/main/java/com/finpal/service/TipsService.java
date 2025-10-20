package com.finpal.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TipsService {
    public List<Map<String, String>> generateBasicTips() {
        List<Map<String, String>> tips = new ArrayList<>();
        tips.add(Map.of(
                "text", "Set a weekly snack budget (e.g., $15) and track it in FinPal.",
                "reason", "Food often becomes a silent spend category for teens."
        ));
        tips.add(Map.of(
                "text", "Try a 'no-spend Friday' challenge this week.",
                "reason", "Building habits is more important than big cuts."
        ));
        tips.add(Map.of(
                "text", "Move spare change into a small savings goal (e.g., $5/week).",
                "reason", "Tiny, consistent deposits add up fast."
        ));
        return tips;
    }
}

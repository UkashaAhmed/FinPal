package com.finpal.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CategorizerService {
    public record CategoryScore(String name, double confidence) {}
    public CategoryScore categorize(String text) {
        String t = (text == null ? "" : text).toLowerCase();
        if (t.contains("mcdonald") || t.contains("kfc") || t.contains("pizza") || t.contains("starbucks"))
            return new CategoryScore("Food & Snacks", 0.92);
        if (t.contains("uber") || t.contains("lyft") || t.contains("metro") || t.contains("bus"))
            return new CategoryScore("Transport", 0.85);
        if (t.contains("game") || t.contains("steam") || t.contains("xbox") || t.contains("playstation"))
            return new CategoryScore("Gaming", 0.88);
        if (t.contains("amazon") || t.contains("target") || t.contains("walmart"))
            return new CategoryScore("Shopping", 0.70);
        return new CategoryScore("Uncategorized", 0.30);
    }
}

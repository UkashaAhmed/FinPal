package com.finpal.service;

import com.finpal.domain.*;
import com.finpal.repo.AccountRepo;
import com.finpal.repo.CategoryRepo;
import com.finpal.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootstrapService {
    private final UserRepo userRepo;
    private final AccountRepo accountRepo;
    private final CategoryRepo categoryRepo;

    @PostConstruct
    public void init() {
        // Create a default teen user & account for quick demo
        if (userRepo.findByEmail("teen@finpal.app").isEmpty()) {
            User teen = userRepo.save(User.builder().name("Fin Teen").email("teen@finpal.app").role(Role.TEEN).build());
            accountRepo.save(Account.builder().user(teen).name("Main").allowanceCents(10000).period("MONTHLY").build());
        }
        // Seed system categories
        String[] base = {"Food & Snacks","Transport","Gaming","Shopping","Uncategorized"};
        for (String c : base) {
            categoryRepo.findByUserIsNullAndName(c).orElseGet(() -> categoryRepo.save(Category.builder()
                    .user(null).name(c).icon("🏷").isSystem(true).build()));
        }
    }
}

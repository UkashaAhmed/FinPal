package com.finpal.service;

import com.finpal.domain.*;
import com.finpal.dto.TransactionDto;
import com.finpal.repo.AccountRepo;
import com.finpal.repo.CategoryRepo;
import com.finpal.repo.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepo txRepo;
    private final AccountRepo accountRepo;
    private final CategoryRepo categoryRepo;

    public Page<TransactionDto> list(UUID accountId, LocalDate from, LocalDate to, Pageable pageable) {
        Account acct = accountRepo.findById(accountId).orElseThrow();
        return txRepo.findByAccountAndDateBetween(acct, from, to, pageable)
                .map(tx -> new TransactionDto(tx.getId(), tx.getDate(), tx.getMerchant(),
                        tx.getDescription(), tx.getAmountCents(), tx.getType(),
                        tx.getCategory() != null ? tx.getCategory().getName() : null, tx.getConfidence()));
    }

    public Transaction create(Account account, LocalDate date, String merchant,
                              String description, long amountCents, TxType type,
                              Category category, Double confidence, Source source) {
        Transaction tx = Transaction.builder()
                .account(account).date(date).merchant(merchant).description(description)
                .amountCents(amountCents).type(type).category(category).confidence(confidence).source(source)
                .build();
        return txRepo.save(tx);
    }

    public Optional<Transaction> find(UUID id) { return txRepo.findById(id); }
    public Transaction save(Transaction tx) { return txRepo.save(tx); }
    public Account getDefaultAccount() { return accountRepo.findAll().stream().findFirst().orElseThrow(); }
    public Category findOrCreateUserCategory(User user, String name) {
        return categoryRepo.findByUserAndName(user, name).orElseGet(() -> categoryRepo.save(Category.builder()
                .user(user).name(name).icon("🏷").isSystem(false).build()));
    }
    public Category findSystemCategory(String name) {
        return categoryRepo.findByUserIsNullAndName(name).orElse(null);
    }
}

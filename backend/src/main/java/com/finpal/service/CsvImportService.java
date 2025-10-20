package com.finpal.service;

import com.finpal.domain.*;
import com.finpal.dto.ImportResult;
import com.finpal.repo.AccountRepo;
import com.finpal.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class CsvImportService {
    private final TransactionService txService;
    private final CategorizerService categorizer;
    private final UserRepo userRepo;
    private final AccountRepo accountRepo;

    public ImportResult importFile(MultipartFile file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String header = br.readLine();
            if (header == null) return new ImportResult(0,0,0, "Empty file");
            // Expected: Date, Merchant, Description, Amount, Type
            AtomicInteger total = new AtomicInteger();
            AtomicInteger created = new AtomicInteger();
            AtomicInteger categorized = new AtomicInteger();

            Account account = txService.getDefaultAccount();
            User user = account.getUser();

            String line;
            while ((line = br.readLine()) != null) {
                total.incrementAndGet();
                String[] parts = line.split(",", -1);
                if (parts.length < 5) continue;
                LocalDate date = LocalDate.parse(parts[0].trim());
                String merchant = parts[1].trim();
                String description = parts[2].trim();
                double amount = Double.parseDouble(parts[3].trim());
                String typeStr = parts[4].trim().toUpperCase();
                TxType type = "CREDIT".equals(typeStr) ? TxType.CREDIT : TxType.DEBIT;
                long amountCents = Math.round(amount * 100);

                var guess = categorizer.categorize((merchant + " " + description).trim());
                Category cat = txService.findSystemCategory(guess.name());
                if (cat == null) cat = txService.findOrCreateUserCategory(user, guess.name());

                txService.create(account, date, merchant, description, amountCents, type, cat, guess.confidence(), Source.CSV);
                created.incrementAndGet();
                if (guess.confidence() >= 0.5) categorized.incrementAndGet();
            }
            return new ImportResult(total.get(), created.get(), categorized.get(), "OK");
        } catch (Exception e) {
            return new ImportResult(0,0,0, "Error: " + e.getMessage());
        }
    }
}

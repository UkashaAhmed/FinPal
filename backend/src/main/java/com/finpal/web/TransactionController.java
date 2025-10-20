package com.finpal.web;

import com.finpal.domain.Account;
import com.finpal.domain.Transaction;
import com.finpal.domain.TxType;
import com.finpal.dto.ImportResult;
import com.finpal.dto.TransactionDto;
import com.finpal.repo.AccountRepo;
import com.finpal.service.CsvImportService;
import com.finpal.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TransactionController {
    private final TransactionService service;
    private final CsvImportService csv;
    private final AccountRepo accountRepo;

    @PostMapping("/import")
    public ImportResult importCsv(@RequestPart("file") MultipartFile file) { return csv.importFile(file); }

    @GetMapping
    public Page<TransactionDto> list(
            @RequestParam("accountId") UUID accountId,
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            Pageable pageable
    ) {
        return service.list(accountId, LocalDate.parse(from), LocalDate.parse(to), pageable);
    }


    record CreateTx(UUID accountId, String date, String merchant, String description, long amountCents, String type){}
    @PostMapping
    public Transaction create(@RequestBody CreateTx req) {
        Account acct = accountRepo.findById(req.accountId()).orElseThrow();
        return service.create(acct, LocalDate.parse(req.date()), req.merchant(), req.description(),
                req.amountCents(), "CREDIT".equalsIgnoreCase(req.type()) ? TxType.CREDIT : TxType.DEBIT,
                null, null, com.finpal.domain.Source.MANUAL);
    }
}

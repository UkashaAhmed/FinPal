package com.finpal.dto;

import com.finpal.domain.TxType;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionDto(
        UUID id,
        LocalDate date,
        String merchant,
        String description,
        long amountCents,
        TxType type,
        String category,
        Double confidence
) {}

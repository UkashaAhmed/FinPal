package com.finpal.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity @Table(name = "transactions", indexes = {
        @Index(columnList = "date"),
        @Index(columnList = "category_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Transaction {
    @Id @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private Account account;

    private LocalDate date;

    private String merchant;

    @Column(length = 512)
    private String description;

    private long amountCents;

    @Enumerated(EnumType.STRING)
    private TxType type;

    @ManyToOne
    private Category category;

    private Double confidence;

    @Enumerated(EnumType.STRING)
    private Source source;

    @CreationTimestamp
    private Instant createdAt;
}

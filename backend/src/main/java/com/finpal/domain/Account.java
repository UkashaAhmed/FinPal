package com.finpal.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "accounts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Account {
    @Id @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private User user;

    private String name;

    private long allowanceCents;

    private String period; // WEEKLY or MONTHLY

    @CreationTimestamp
    private Instant createdAt;
}

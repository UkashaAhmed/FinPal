package com.finpal.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "categories",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","name"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Category {
    @Id @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user; // null for system category

    @Column(nullable=false)
    private String name;

    private String icon;

    private boolean isSystem;

    @CreationTimestamp
    private Instant createdAt;
}

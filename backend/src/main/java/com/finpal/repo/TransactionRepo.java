package com.finpal.repo;
import com.finpal.domain.Account;
import com.finpal.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.UUID;
public interface TransactionRepo extends JpaRepository<Transaction, UUID> {
    Page<Transaction> findByAccountAndDateBetween(Account account, LocalDate from, LocalDate to, Pageable pageable);
}

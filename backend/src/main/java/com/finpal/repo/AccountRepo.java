package com.finpal.repo;
import com.finpal.domain.Account;
import com.finpal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
public interface AccountRepo extends JpaRepository<Account, UUID> {
    List<Account> findByUser(User user);
}

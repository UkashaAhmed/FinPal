package com.finpal.repo;
import com.finpal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;
public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}

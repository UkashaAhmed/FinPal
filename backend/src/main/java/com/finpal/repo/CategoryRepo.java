package com.finpal.repo;
import com.finpal.domain.Category;
import com.finpal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface CategoryRepo extends JpaRepository<Category, UUID> {
    List<Category> findByUserIsNullOrUser(User user);
    Optional<Category> findByUserAndName(User user, String name);
    Optional<Category> findByUserIsNullAndName(String name);
}

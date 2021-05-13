package hu.bme.aut.javaweb.forum.repository;

import hu.bme.aut.javaweb.forum.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}

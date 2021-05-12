package hu.bme.aut.javaweb.forum.datasource;

import hu.bme.aut.javaweb.forum.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDataSource extends JpaRepository<Category, Long> {
    Category findByName(String name);
}

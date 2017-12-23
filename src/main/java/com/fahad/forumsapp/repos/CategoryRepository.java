package com.fahad.forumsapp.repos;

import com.fahad.forumsapp.models.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Fahad Ahmed
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
}

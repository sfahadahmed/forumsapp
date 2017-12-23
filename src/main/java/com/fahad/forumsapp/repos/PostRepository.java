package com.fahad.forumsapp.repos;

import com.fahad.forumsapp.models.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Fahad Ahmed
 */
public interface PostRepository extends CrudRepository<Post, Long> {
}

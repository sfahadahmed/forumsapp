package com.fahad.forumsapp.repos;

import com.fahad.forumsapp.models.Topic;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Fahad Ahmed
 */
public interface TopicRepository extends CrudRepository<Topic, Long> {
}

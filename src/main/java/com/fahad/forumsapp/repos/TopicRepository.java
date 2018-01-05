package com.fahad.forumsapp.repos;

import com.fahad.forumsapp.models.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Fahad Ahmed
 */
public interface TopicRepository extends CrudRepository<Topic, Long> {
    public List<Topic> findByTitle(String title);
    public List<Topic> findByDescription(String description);
}

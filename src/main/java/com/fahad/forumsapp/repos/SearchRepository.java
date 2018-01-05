package com.fahad.forumsapp.repos;

import com.fahad.forumsapp.models.Post;
import com.fahad.forumsapp.models.Topic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Fahad Ahmed
 */
@Repository
public class SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Topic> findTopics(String text) {
        return entityManager.createQuery("from Topic as e where lower(e.title) like lower(:text) or lower(e.description) like lower(:text)")
            .setParameter("text", "%"+text+"%").getResultList();
    }

    public List<Post> findPosts(String text) {
        return entityManager.createQuery("from Post as e where lower(e.text) like lower(:text)")
                .setParameter("text", "%"+text+"%").getResultList();
    }
}

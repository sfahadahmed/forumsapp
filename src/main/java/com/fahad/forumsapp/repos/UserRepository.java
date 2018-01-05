package com.fahad.forumsapp.repos;

import com.fahad.forumsapp.models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Fahad Ahmed
 */
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByLastName(String lastName);
    public User findByUsername(String username);
    public User findByEmail(String email);
}
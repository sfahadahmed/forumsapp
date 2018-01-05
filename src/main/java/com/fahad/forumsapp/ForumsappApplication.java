package com.fahad.forumsapp;

import com.fahad.forumsapp.models.*;
import com.fahad.forumsapp.repos.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class ForumsappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumsappApplication.class, args);
	}

    @Bean
    public CommandLineRunner demo(CategoryRepository categoryRepository,
        TopicRepository topicRepository, PostRepository postRepository,
        UserRepository userRepository, RoleRepository roleRepository){
        return (args) -> {
            Date date = new Date();

            //
            // Roles
            //
            Role roleAdmin = new Role("Administrator", "ADMIN");
            Role roleUser = new Role("User", "USER");
            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);

            //
            // Users
            //
            User userAdmin = new User("System", "Admin", "admin@nosite.com", "admin", "password", roleAdmin, date);
            User userGuest = new User("Guest", "User", "guest@nosite.com", "guest", "password", roleUser, date);
            User anotherUser = new User("Fahad", "Ahmed", "sfahadahmed@gmail.com", "fahad", "password", roleUser, date);
            userRepository.save(userAdmin);
            userRepository.save(userGuest);
            userRepository.save(anotherUser);

            //
            // Category: General
            //
            Category categoryGeneral = new Category("General", "A general discussion just about anything.", date, userAdmin);
            categoryRepository.save(categoryGeneral);

            Topic topic1 = new Topic("My first topic", "Hi everyone! Just wanted to test new topic.", date, true, userAdmin, categoryGeneral);
            Topic topic2 = new Topic("My second topic", "Hi everyone! Just wanted to test new topic.", date, false, userGuest, categoryGeneral);
            Topic topic3 = new Topic("My third topic", "Hi everyone! Just wanted to test new topic.", date, true, userGuest, categoryGeneral);

            topicRepository.save(topic1);
            topicRepository.save(topic2);
            topicRepository.save(topic3);

            //
            // Posts
            //
            Post post1 = new Post("Hi! this is my first post.", date, userGuest);
            post1.setTopic(topic1);
            Post post2 = new Post("Hi! this is another post.", date, userAdmin);
            post2.setTopic(topic1);
            Post post3 = new Post("Hi! this is my first post.", date, userGuest);
            post3.setTopic(topic2);

            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);

            //
            // Category: Feedback
            //
            Category categoryFeedback = new Category("Feedback", "Discussion about this site, its organization, how it works, and how we can improve it.", date, userAdmin);
            categoryRepository.save(categoryFeedback);

            Topic topic4 = new Topic("My fourth topic", "Hi everyone! Just wanted to test new topic.", date, false, userAdmin, categoryFeedback);
            Topic topic5 = new Topic("My fifth topic", "Hi everyone! Just wanted to test new topic.", date, true, userGuest, categoryFeedback);
            Topic topic6 = new Topic("My sixth topic", "Hi everyone! Just wanted to test new topic.", date, false, userGuest, categoryFeedback);

            topicRepository.save(topic4);
            topicRepository.save(topic5);
            topicRepository.save(topic6);
        };
    }
}

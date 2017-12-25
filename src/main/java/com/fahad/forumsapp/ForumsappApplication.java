package com.fahad.forumsapp;

import com.fahad.forumsapp.models.Category;
import com.fahad.forumsapp.models.Topic;
import com.fahad.forumsapp.models.User;
import com.fahad.forumsapp.repos.CategoryRepository;
import com.fahad.forumsapp.repos.PostRepository;
import com.fahad.forumsapp.repos.TopicRepository;
import com.fahad.forumsapp.repos.UserRepository;
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
        UserRepository userRepository){
        return (args) -> {
            Date date = new Date();

            //
            // Users
            //
            User userAdmin = new User("Sys", "Admin", "admin@nosite.com", "abcd1234", "ROLE_ADMIN", date);
            User userGuest = new User("Guest", "User", "guest@nosite.com", "abcd1234", "ROLE_USER", date);
            userRepository.save(userAdmin);
            userRepository.save(userGuest);

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

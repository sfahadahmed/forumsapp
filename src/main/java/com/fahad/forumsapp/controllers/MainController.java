package com.fahad.forumsapp.controllers;

import com.fahad.forumsapp.models.Category;
import com.fahad.forumsapp.models.Post;
import com.fahad.forumsapp.models.Topic;
import com.fahad.forumsapp.models.User;
import com.fahad.forumsapp.repos.CategoryRepository;
import com.fahad.forumsapp.repos.PostRepository;
import com.fahad.forumsapp.repos.TopicRepository;
import com.fahad.forumsapp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Fahad Ahmed
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute("Post")
    public Post populatePost(){
        Post post = new Post();
        post.setTopic(populateTopic());
        return post;
    }

    @ModelAttribute("Topic")
    public Topic populateTopic(){
        Topic topic = new Topic();
        topic.setCategory(populateCategory());
        return topic;
    }

    @ModelAttribute("Category")
    public Category populateCategory(){
        Category category = new Category();
        return category;
    }

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        return "forums/main";
    }

    @GetMapping("/showtopic")
    public String showTopic(@RequestParam long id, @ModelAttribute Post post, BindingResult errors, Model model){
        Topic topic = topicRepository.findOne(id);

        if(errors.hasErrors()){
            return "forums/main";
        }
        if(topic != null) {
            post.setTopic(topic);

            model.addAttribute("topic", topic);
            model.addAttribute("post", post);
            return "forums/showtopic";
        }
        else {
            return "forums/main";
        }
    }

    @PostMapping("/showtopic")
    public String saveShowTopic(@ModelAttribute Post post, BindingResult errors, Model model){

        // @RequestParam long topicId,
        if(errors.hasErrors()){
            return "forums/showtopic";
        }
        else {
            Topic topic = topicRepository.findOne(post.getTopic().getId());

            if (topic != null) {
                // TODO: use logged-in user instead
                User user = userRepository.findOne(1L);
                post.setCreatedBy(user);

                Date date = new Date();
                post.setCreationDate(date);

                topic.getPosts().add(post);

                post.setTopic(topic);
                post = postRepository.save(post);

                model.addAttribute("topic", topic);
                model.addAttribute("post", post);

                return "forums/showtopic";
            } else {
                return "forums/main";
            }
        }
    }

    @GetMapping("/createtopic")
    public String createTopic(@RequestParam long categoryId, @ModelAttribute Topic topic, BindingResult errors, Model model){
        Category category = categoryRepository.findOne(categoryId);

        if(errors.hasErrors() || category == null){
            return "forums/main";
        }
        else {
            topic.setCategory(category);

            model.addAttribute("category", category);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("topic", topic);

            return "forums/createtopic";
        }
    }

    @PostMapping("/createtopic")
    public String saveCreateTopic(@ModelAttribute Topic topic, BindingResult errors, Model model){

        if(errors.hasErrors()){
            model.addAttribute("categories", categoryRepository.findAll());
            return "forums/main";
        }
        else {
            topicRepository.save(topic);
            model.addAttribute("topic", topic);
            model.addAttribute("categories", categoryRepository.findAll());
            return "forums/main";
        }
    }
}

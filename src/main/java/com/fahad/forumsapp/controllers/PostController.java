package com.fahad.forumsapp.controllers;

import com.fahad.forumsapp.models.Post;
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
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @ModelAttribute("Post")
    public Post populatePost(){
        Post post = new Post();
        return post;
    }

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "posts/list";
    }

    @GetMapping("/create")
    public String showCreateForm(@ModelAttribute Post post, Model model) {
        model.addAttribute("topics", topicRepository.findAll());
        model.addAttribute("post", post);

        return "posts/create";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam long id, Model model) {
        Post post = postRepository.findOne(id);

        if(post != null) {
            model.addAttribute("post", post);
            return "posts/edit";
        }
        else {
            model.addAttribute("post", postRepository.findAll());
            return "posts/list";
        }
    }

    @PostMapping("/create")
    public String saveCreateForm(@ModelAttribute Post post, BindingResult errors, Model model) {

        if(errors.hasErrors() || post.getTopic() == null){
            return "/create";
        }
        else {
            // TODO: use logged-in user instead
            User user = userRepository.findOne(1L);
            post.setCreatedBy(user);

            Date date = new Date();
            post.setCreationDate(date);

            postRepository.save(post);

            model.addAttribute("post", post);
            model.addAttribute("posts", postRepository.findAll());

            return "posts/list";
        }
    }

    @PostMapping("/edit")
    public String saveEditForm(@ModelAttribute Post post, BindingResult errors, Model model) {

        if(errors.hasErrors()){
            return "/create";
        }
        else {
            Post existingPost = postRepository.findOne(post.getId());

            existingPost.setText(post.getText());
            postRepository.save(existingPost);

            model.addAttribute("post", existingPost);
            model.addAttribute("posts", postRepository.findAll());

            return "posts/list";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long id, Model model) {
        postRepository.delete(id);

        model.addAttribute("posts", postRepository.findAll());

        return "posts/list";
    }
}

package com.fahad.forumsapp.controllers;

import com.fahad.forumsapp.models.Topic;
import com.fahad.forumsapp.repos.CategoryRepository;
import com.fahad.forumsapp.repos.PostRepository;
import com.fahad.forumsapp.repos.TopicRepository;
import com.fahad.forumsapp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        return "forums/main";
    }

    @GetMapping("/showtopic")
    public String viewTopic(@RequestParam long id, Model model){
        Topic topic = topicRepository.findOne(id);

        if(topic != null) {
            model.addAttribute("topic", topic);
            return "forums/showtopic";
        }
        else {
            return "forums/main";
        }
    }
}

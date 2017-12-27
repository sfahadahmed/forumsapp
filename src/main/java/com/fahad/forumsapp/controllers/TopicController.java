package com.fahad.forumsapp.controllers;

import com.fahad.forumsapp.models.Category;
import com.fahad.forumsapp.models.Topic;
import com.fahad.forumsapp.models.User;
import com.fahad.forumsapp.repos.CategoryRepository;
import com.fahad.forumsapp.repos.TopicRepository;
import com.fahad.forumsapp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @author Fahad Ahmed
 */
@Controller
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @ModelAttribute("Topic")
    public Topic populateTopic(){
        Topic topic = new Topic();
        return topic;
    }

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("topics", topicRepository.findAll());
        return "topics/list";
    }

    @GetMapping("/create")
    public String showCreateForm(@ModelAttribute Topic topic, Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("topic", topic);

        return "topics/create";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam long id, Model model) {
        Topic topic = topicRepository.findOne(id);

        if(topic != null) {
            model.addAttribute("topic", topic);
            return "topics/edit";
        }
        else {
            model.addAttribute("topic", topicRepository.findAll());
            return "topics/list";
        }
    }

    @PostMapping("/create")
    public String saveCreateForm(@ModelAttribute Topic topic, BindingResult errors, Model model) {

        if(errors.hasErrors() || topic.getCategory() == null){
            return "/create";
        }
        else {
            // TODO: use logged-in user instead
            User user = userRepository.findOne(1L);
            topic.setCreatedBy(user);

            Date date = new Date();
            topic.setCreationDate(date);

            topicRepository.save(topic);

            model.addAttribute("topic", topic);
            model.addAttribute("topics", topicRepository.findAll());

            return "topics/list";
        }
    }

    @PostMapping("/edit")
    public String saveEditForm(@ModelAttribute Topic topic, BindingResult errors, Model model) {

        if(errors.hasErrors()){
            return "/create";
        }
        else {
            Topic existingTopic = topicRepository.findOne(topic.getId());

            existingTopic.setTitle(topic.getTitle());
            existingTopic.setDescription(topic.getDescription());

            topicRepository.save(existingTopic);

            model.addAttribute("topic", existingTopic);
            model.addAttribute("topics", topicRepository.findAll());

            return "topics/list";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long id, Model model) {
        topicRepository.delete(id);

        model.addAttribute("topics", topicRepository.findAll());

        return "topics/list";
    }
}

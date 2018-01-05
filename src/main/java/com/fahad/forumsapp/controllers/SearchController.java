package com.fahad.forumsapp.controllers;

import com.fahad.forumsapp.repos.SearchRepository;
import com.fahad.forumsapp.repos.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Fahad Ahmed
 */
@Controller
public class SearchController {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    SearchRepository searchRepository;

    @PostMapping("/search")
    public String search(@RequestParam String text, Model model){
        model.addAttribute("topics", searchRepository.findTopics(text));
        return "search";
    }
}

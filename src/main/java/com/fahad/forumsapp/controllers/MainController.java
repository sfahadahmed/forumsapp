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
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @GetMapping({"", "/", "/home"})
    public String home(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        return "forums/main";
    }

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "auth/login";
    }

    @GetMapping("/403")
    public String error403(){
        return "errors/403";
    }
}

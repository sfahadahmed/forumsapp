package com.fahad.forumsapp.controllers;

import com.fahad.forumsapp.repos.CategoryRepository;
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

    @GetMapping({"", "/forums"})
    public String home(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        return "forums/main";
    }
}

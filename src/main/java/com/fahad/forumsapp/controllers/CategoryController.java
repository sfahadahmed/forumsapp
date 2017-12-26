package com.fahad.forumsapp.controllers;

import com.fahad.forumsapp.models.Category;
import com.fahad.forumsapp.models.User;
import com.fahad.forumsapp.repos.CategoryRepository;
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
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String showList(Map<String, Object> model) {
        model.put("categories", categoryRepository.findAll());
        return "categories/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Map<String, Object> model) {
        model.put("category", new Category());
        return "categories/create";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam long id, Map<String, Object> model) {
        Category category = categoryRepository.findOne(id);

        if(category != null) {
            model.put("category", category);
            return "categories/edit";
        }
        else {
            return "categories/list";
        }
    }

    @PostMapping("/create")
    public String saveCreateForm(@ModelAttribute Category category, BindingResult errors, Model model) {

        // TODO: use logged-in user instead
        User user = userRepository.findOne(1L);
        category.setCreatedBy(user);

        Date date = new Date();
        category.setCreationDate(date);

        categoryRepository.save(category);

        model.addAttribute("category", category);
        model.addAttribute("categories", categoryRepository.findAll());

        return "categories/list";
    }

    @PostMapping("/edit")
    public String saveEditForm(@ModelAttribute Category category, BindingResult errors, Model model) {

        Category existingCategory = categoryRepository.findOne(category.getId());

        existingCategory.setTitle(category.getTitle());
        existingCategory.setDescription(category.getDescription());

        categoryRepository.save(existingCategory);

        model.addAttribute("category", existingCategory);
        model.addAttribute("categories", categoryRepository.findAll());

        return "categories/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long id, Map<String, Object> model) {
        categoryRepository.delete(id);

        model.put("categories", categoryRepository.findAll());

        return "categories/list";
    }
}

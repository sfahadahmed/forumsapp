package com.fahad.forumsapp.controllers;

import com.fahad.forumsapp.models.User;
import com.fahad.forumsapp.repos.RoleRepository;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @ModelAttribute("User")
    public User populateUser(){
        User user = new User();
        return user;
    }

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "users/list";
    }

    @GetMapping("/create")
    public String showCreateForm(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());

        return "users/create";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam long id, Model model) {
        User user = userRepository.findOne(id);

        if(user != null) {
            model.addAttribute("user", user);
            model.addAttribute("roles", roleRepository.findAll());
            return "users/edit";
        }
        else {
            model.addAttribute("users", userRepository.findAll());
            return "users/list";
        }
    }

    @PostMapping("/create")
    public String saveCreateForm(@ModelAttribute User user, BindingResult errors, Model model) {

        if(errors.hasErrors()){
            return "/create";
        }
        else {
            Date date = new Date();
            user.setCreationDate(new Date());

            userRepository.save(user);

            model.addAttribute("user", user);
            model.addAttribute("users", userRepository.findAll());

            return "users/list";
        }
    }

    @PostMapping("/edit")
    public String saveEditForm(@ModelAttribute User user, BindingResult errors, Model model) {

        if(errors.hasErrors()){
            return "/create";
        }
        else {
            User existingUser = userRepository.findOne(user.getId());

            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());

            if(user.getRole() != null)
                existingUser.setRole(user.getRole());

            if(user.getPassword() != null && user.getPassword() != "")
                existingUser.setPassword(user.getPassword());

            userRepository.save(existingUser);

            model.addAttribute("user", existingUser);
            model.addAttribute("users", userRepository.findAll());

            return "users/list";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long id, Model model) {
        userRepository.delete(id);

        model.addAttribute("users", userRepository.findAll());

        return "users/list";
    }
}

package com.taskmanagement.task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.taskmanagement.task.dto.UserDto;
import com.taskmanagement.task.models.User;
import com.taskmanagement.task.service.UserService;

import jakarta.validation.Valid;

@Controller
public class HomeControllers {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

 

    @GetMapping("/user/")
    public String userPage() {
        return "user";
    }

    @PostMapping("/registration")
    public String registration(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null)
            result.rejectValue("email", null,
                    "User already registered !!!");

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/registration";
        }

        if (userDto.getRole().equals("ADMIN")) {
            userService.saveAdmin(userDto);
        } else if (userDto.getRole().equals("CONSULTANT")) {
            userService.saveConsultant(userDto);
        }

        return "redirect:/registration?success";
    }
}
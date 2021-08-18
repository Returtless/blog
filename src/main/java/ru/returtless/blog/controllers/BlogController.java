package ru.returtless.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {
    @GetMapping("/blog")
    public String about(Model model) {
        model.addAttribute("title", "Главная страница");
        return "blog";
    }
}

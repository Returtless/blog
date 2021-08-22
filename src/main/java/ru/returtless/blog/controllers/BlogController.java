package ru.returtless.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.returtless.blog.models.Post;
import ru.returtless.blog.repo.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;


    @GetMapping("/blog")
    public String about(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("title", "Главная страница");
        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        model.addAttribute("title", "Добавление статьи");
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String announce, @RequestParam String full_text, Model model){
        Post post = new Post(title, announce, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        model.addAttribute("title", "Просмотр статьи");
        Optional<Post> post = postRepository.findById(id);
        List<Post> posts = new ArrayList<>();
        post.ifPresent(posts :: add);
        model.addAttribute("post",posts);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        model.addAttribute("title", "Редактирование статьи");
        Optional<Post> post = postRepository.findById(id);
        List<Post> posts = new ArrayList<>();
        post.ifPresent(posts :: add);
        model.addAttribute("post",posts);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostAdd(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String announce, @RequestParam String full_text, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setAnnounce(announce);
        post.setText(full_text);
        post.setTitle(title);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostRemove(@PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }
}

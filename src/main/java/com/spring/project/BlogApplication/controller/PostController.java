package com.spring.project.BlogApplication.controller;

import com.spring.project.BlogApplication.entity.Comment;
import com.spring.project.BlogApplication.entity.Post;
import com.spring.project.BlogApplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/list")
    public String allPost(Model model) {

        //find all posts
        List<Post> posts = postService.findAll();

        //set to model
        model.addAttribute("posts", posts);

        return "posts-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        // create model attribute to bind form data
        Post post = new Post();
        model.addAttribute("post", post);

        return "new-post";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute("post") Post post,
                           @RequestParam("tagNames") String tagNames) {

        //save the new post
        postService.save(post, tagNames);
        return "redirect:/posts/list";
    }

    @GetMapping("/view")
    public String viewPost(@RequestParam("postId") int id, Model model) {
        // find post by id
        Post post = postService.findById(id);
        Comment newComment = new Comment();
        newComment.setPost(post);

        //add to model
        model.addAttribute("post", post);
        model.addAttribute("newComment", newComment);

        return "view-post";
    }

    @GetMapping("/edit")
    public String editPost(@RequestParam("postId") int id, Model model) {

        //find post from service
        Post post = postService.findById(id);

        //add employee to model
        model.addAttribute("post", post);

        //send over to form
        return "new-post";
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam("postId") int id) {
        postService.deleteById(id);
        return "redirect:/posts/list";
    }
}

package com.spring.project.BlogApplication.controller;

import com.spring.project.BlogApplication.entity.Comment;
import com.spring.project.BlogApplication.entity.Post;
import com.spring.project.BlogApplication.service.CommentService;
import com.spring.project.BlogApplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/add")
    public String addComment(@ModelAttribute("newComment") Comment comment) {


        commentService.save(comment);

        return "redirect:/posts/view?postId=" + comment.getPost().getId();
    }

    @GetMapping("/edit")
    public String editComment(@RequestParam("commentId") int id, Model model) {
        Comment theComment = commentService.findById(id);
        model.addAttribute("comment", theComment);
        return "edit-comment";
    }

    @PostMapping("/update")
    public String updateComment(@ModelAttribute("theComment") Comment comment) {
        commentService.save(comment);
        return "redirect:/posts/view?postId=" + comment.getPost().getId();
    }

    @GetMapping("/delete")
    public String deleteComment(@RequestParam("commentId") int id) {
        Comment comment = commentService.findById(id);
        int postId = comment.getPost().getId();
        commentService.deleteById(id);
        return "redirect:/posts/view?postId=" + postId;
    }
}

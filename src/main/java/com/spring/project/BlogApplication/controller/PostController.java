package com.spring.project.BlogApplication.controller;

import com.spring.project.BlogApplication.entity.Comment;
import com.spring.project.BlogApplication.entity.Post;
import com.spring.project.BlogApplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/list")
    public String allPost(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "authors", required = false) List<String> authors, // authors to select from dropdown
            @RequestParam(value = "tags", required = false) List<String> tags, // tags to select from dropdown
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortDirection", defaultValue = "desc") String sortDirection,
            Model model) {

        //normalize empty filter before passing bcz if we pass null list it cause error
        if (authors != null && authors.isEmpty()) authors = null;
        if (tags != null && tags.isEmpty()) tags = null;
        if (search != null && search.trim().isEmpty()) {
            search = null;
        }


        //find all posts
        Page<Post> postsPage;

//        these are combined in filterPosts method so need to use them here
//        if (search != null && !search.isEmpty()) {
//            postsPage = postService.searchPosts(search, page, size, sortDirection);
//        }
//        else {
//            postsPage = postService.findAll(page, size, sortDirection);
//        }

        postsPage = postService.filterPosts(search, authors, tags, page, size, sortDirection);

//        Core post data
        //set to model contains page info, total pages, posts etc. if we want to extract in thymeleaf
        model.addAttribute("postsPage", postsPage);
        //contains list of posts
        model.addAttribute("posts", postsPage.getContent());
        //current page
        model.addAttribute("currentPage", page);
        //total page
        model.addAttribute("totalPages", postsPage.getTotalPages());

//        For UI filters
        //to retain search text in input box
        model.addAttribute("search", search);
        //to add sort direction
        model.addAttribute("sortDirection", sortDirection);
        // to add authors
        model.addAttribute("selectedAuthors", authors);
        // to add tags
        model.addAttribute("selectedTags", tags);

//        Populate dropdowns
        //list of authors
        model.addAttribute("authors", postService.getAllAuthors());
        //list of tags
        model.addAttribute("tags", postService.getAllTags());

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

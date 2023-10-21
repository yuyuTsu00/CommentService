package com.intuit.interview.commentservice.Post.Controller;

import com.intuit.interview.commentservice.Post.Exception.PostNotFoundException;
import com.intuit.interview.commentservice.Post.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.intuit.interview.commentservice.Post.Model.Post;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    private final PostService postService;
    PostController(PostService postService)
    {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> postDetails(@PathVariable("id") String postId) throws PostNotFoundException
    {
        // fetch details from post table and return
        return new ResponseEntity<>(postService.postDetails(postId), HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable("id") String postId) throws PostNotFoundException
    {
        // remove post from table with given id
        return new ResponseEntity<>(postService.deletePost(postId), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Post> newPost(@RequestBody Post post)
    {
        // insert new post in the post table
        return new ResponseEntity<>(postService.newPost(post), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Post> updatePost(@RequestBody Post post) throws PostNotFoundException
    {
        // update the already present post
        return new ResponseEntity<>(postService.updatePost(post), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Post>> getAllPostOfUser(@PathVariable("id") String userId)
    {
        return new ResponseEntity<>(postService.getAllPostOfUser(userId), HttpStatus.FOUND);
    }
}

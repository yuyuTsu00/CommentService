package com.intuit.interview.commentservice.Post.Controller;

import com.intuit.interview.commentservice.CommonUtility.PaginatedResponse;
import com.intuit.interview.commentservice.Post.DTO.NewPostDto;
import com.intuit.interview.commentservice.Post.Exception.PostNotFoundException;
import com.intuit.interview.commentservice.Post.Service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.intuit.interview.commentservice.Post.Model.Post;

@RestController
@RequestMapping("post")
public class PostController {

    private final PostService postService;
    PostController(PostService postService)
    {
        this.postService = postService;
    }

    @Operation(summary = "get post by id", description = "post id should be valid")
    @GetMapping("/{id}")
    public ResponseEntity<Post> postDetails(@PathVariable("id") String postId) throws PostNotFoundException
    {
        // fetch details from post table and return
        return new ResponseEntity<>(postService.postDetails(postId), HttpStatus.OK);
    }

    @Operation(summary = "delete a post by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable("id") String postId) throws PostNotFoundException
    {
        // remove post from table with given id
        return new ResponseEntity<>(postService.deletePost(postId), HttpStatus.OK);
    }

    @Operation(summary = "create new post for a user")
    @PostMapping("/new")
    public ResponseEntity<Post> newPost(@RequestBody NewPostDto post)
    {
        // insert new post in the post table
        return new ResponseEntity<>(postService.newPost(post), HttpStatus.CREATED);
    }

    @Operation(summary = "update an existing post")
    @PutMapping("/update")
    public ResponseEntity<Post> updatePost(@RequestBody Post post) throws PostNotFoundException
    {
        // update the already present post
        return new ResponseEntity<>(postService.updatePost(post), HttpStatus.OK);
    }

    @Operation(summary = "get all post of a user", description = "paginated response")
    @GetMapping("/user/{id}")
    public ResponseEntity<PaginatedResponse<Post>> getAllPostOfUser(@PathVariable("id") String userId, @RequestParam(required = false) Integer start, @RequestParam(required = false) Integer size)
    {
        int skip = start == null ? 0 : start;
        int count = size == null ? 100 : size;

        Pageable pageable = PageRequest.of(skip, count);
        return new ResponseEntity<>(postService.getAllPostOfUser(userId, pageable), HttpStatus.OK);
    }
}

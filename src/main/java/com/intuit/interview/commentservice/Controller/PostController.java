package com.intuit.interview.commentservice.Controller;

import com.intuit.interview.commentservice.Model.Like;
import com.intuit.interview.commentservice.Repositories.PostRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.intuit.interview.commentservice.Model.Post;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("post")
public class PostController {

    private final PostRepository postRepository;
    PostController(PostRepository postRepository)
    {
        this.postRepository = postRepository;
    }

    @GetMapping("/{id}")
    public void postDetails(@PathVariable("id") String postId)
    {
        // fetch details from post table and return
        this.postRepository.findById(postId);

    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") String postId)
    {
        // remove post from table with given id
        Optional<Post> post = postRepository.findById(postId);
        System.out.println(post);
        post.ifPresent(postRepository::delete);
    }

    @PostMapping("/newPost")
    public void newPost(@RequestBody Post post)
    {
        // insert new post in the post table
        postRepository.insert(post);
    }

    @PutMapping("/updatePost")
    public void updatePost(@RequestBody Post post)
    {
        // update the already present post
        post.setLastUpdatedON(Date.from(Instant.now()));
        postRepository.save(post);
    }

    @GetMapping("/userposts/{id}")
    public List<Post> getAllPostOfUser(@PathVariable("id") String userId)
    {
        return postRepository.getAllPostOfUser(userId);
    }

}

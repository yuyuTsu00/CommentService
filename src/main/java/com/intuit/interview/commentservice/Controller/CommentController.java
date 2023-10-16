package com.intuit.interview.commentservice.Controller;

import com.intuit.interview.commentservice.Model.CommentThread;
import com.intuit.interview.commentservice.Repositories.CommentRepository;
import com.intuit.interview.commentservice.Repositories.CommentThreadRepository;
import com.intuit.interview.commentservice.Service.CommentService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.intuit.interview.commentservice.Model.Comment;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final CommentThreadRepository commentThreadRepository;
    CommentController(CommentService commentService, CommentRepository commentRepository, CommentThreadRepository commentThreadRepository)
    {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
        this.commentThreadRepository = commentThreadRepository;
    }


    @GetMapping("/{id}")
    public void commentDetails(@PathVariable("id") String commentId)
    {
        // fetch details from comment table and return
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") String commentId)
    {
        // remove comment from table with given id
    }

    @PostMapping("/newComment")
    public void newComment(@RequestBody Comment comment)
    {
        // insert new comment in the comment table
        commentService.addComment(comment);


    }

    @PutMapping("/updateComment")
    public void updateComment(@RequestBody Comment comment)
    {
        // update the already present comment
        comment.setLastUpdatedON(Date.from(Instant.now()));
        commentRepository.save(comment);
    }

    @GetMapping("/")
    public List<Comment> getAllComments()
    {
        return commentRepository.findAll();
    }

    @GetMapping("/cts")
    public List<CommentThread> getAllCommentThreads()
    {
        return commentThreadRepository.findAll();
    }
}

package com.intuit.interview.commentservice.Comment.Controller;

import com.intuit.interview.commentservice.Comment.Exception.CommentNotFoundException;
import com.intuit.interview.commentservice.Comment.Model.CommentThread;
import com.intuit.interview.commentservice.Comment.Repository.CommentRepository;
import com.intuit.interview.commentservice.Comment.Repository.CommentThreadRepository;
import com.intuit.interview.commentservice.Comment.Service.CommentService;
import com.intuit.interview.commentservice.PaginatedResponse;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.intuit.interview.commentservice.Comment.Model.Comment;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    CommentController(CommentService commentService)
    {
        this.commentService = commentService;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PaginatedResponse<BasicDBObject>> commentThreadsForPost(@PathVariable("id") String postId, @RequestParam(required = false) Integer start)
    {
        // fetch details from comment table and return
        int skip = start == null ? 0 : start;
        return new ResponseEntity<>(commentService.commentThreadsForPost(postId, skip), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> commentDetails(@PathVariable("id") String commentId) throws CommentNotFoundException
    {
        // fetch details from comment table and return
        return new ResponseEntity<>(commentService.commentDetails(commentId), HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable("id") String commentId) throws CommentNotFoundException
    {
        // remove comment from table with given id
        return new ResponseEntity<>(commentService.deleteComment(commentId), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Comment> newComment(@RequestBody Comment comment)
    {
        // insert new comment in the comment table
        return new ResponseEntity<>(commentService.addComment(comment), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment)
    {
        // update the already present comment
        return new ResponseEntity<>(commentService.updateComment(comment), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Comment>> getAllComments()
    {
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.FOUND);
    }

    @GetMapping("/cts")
    public ResponseEntity<List<CommentThread>> getAllCommentThreads()
    {
        return new ResponseEntity<>(commentService.getAllCommentThreads(), HttpStatus.FOUND);
    }
}

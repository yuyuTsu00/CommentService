package com.intuit.interview.commentservice.Comment.Controller;

import com.intuit.interview.commentservice.Comment.DTO.NewCommentDto;
import com.intuit.interview.commentservice.Comment.Exception.CommentNotFoundException;
import com.intuit.interview.commentservice.Comment.Model.CommentThread;
import com.intuit.interview.commentservice.Comment.Service.CommentService;
import com.intuit.interview.commentservice.CommonUtility.PaginatedResponse;
import com.mongodb.BasicDBObject;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.intuit.interview.commentservice.Comment.Model.Comment;


@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    CommentController(CommentService commentService)
    {
        this.commentService = commentService;
    }

    @Operation(summary = "get thread details")
    @GetMapping("/thread/{id}")
    public ResponseEntity<Comment> commentDetails(@PathVariable("id") String commentId) throws CommentNotFoundException
    {
        // fetch details from comment table and return
        return new ResponseEntity<>(commentService.commentDetails(commentId), HttpStatus.OK);
    }

    @Operation(summary = "delete a comment thread")
    @DeleteMapping("/thread/{id}")
    public ResponseEntity<CommentThread> deleteComment(@PathVariable("id") String threadId) throws CommentNotFoundException
    {
        // remove comment from table with given id
        return new ResponseEntity<>(commentService.deleteComment(threadId), HttpStatus.OK);
    }

    @Operation(summary = "add new comment on a post / comment by a user")
    @PostMapping("/new")
    public ResponseEntity<Comment> newComment(@RequestBody NewCommentDto comment, @RequestParam String postId, @RequestParam String userId, @RequestParam(required = false) String threadId)
    {
        // insert new comment in the comment table
        return new ResponseEntity<>(commentService.addComment(comment, postId, userId, threadId), HttpStatus.CREATED);
    }

    @Operation(summary = "update an existing comment")
    @PutMapping("/update")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment)
    {
        // update the already present comment
        return new ResponseEntity<>(commentService.updateComment(comment), HttpStatus.OK);
    }

    @Operation(summary = "get all comment on a post", description = "paginated response")
    @GetMapping("/post/{id}")
    public ResponseEntity<PaginatedResponse<BasicDBObject>> commentThreadsForPost(@PathVariable("id") String postId, @RequestParam(required = false) Integer start, @RequestParam(required = false) Integer size)
    {
        // fetch details from comment table and return
        int skip = start == null ? 0 : start;
        int count = size == null ? 100 : size;

        Pageable pageable = PageRequest.of(skip, count);

        return new ResponseEntity<>(commentService.commentThreadsForPost(postId, pageable), HttpStatus.OK);
    }

    @Operation(summary = "get all comment on a comment", description = "paginated response")
    @GetMapping("/comment/{id}")
    public ResponseEntity<PaginatedResponse<BasicDBObject>> commentThreadsForComment(@PathVariable("id") String commentThreadId, @RequestParam(required = false) Integer start, @RequestParam(required = false) Integer size)
    {
        // fetch details from user table and return
        int skip = start == null ? 0 : start;
        int count = size == null ? 100 : size;

        Pageable pageable = PageRequest.of(skip, count);
        return new ResponseEntity<>(commentService.commentThreadsForComment(commentThreadId, pageable), HttpStatus.OK);
    }
}

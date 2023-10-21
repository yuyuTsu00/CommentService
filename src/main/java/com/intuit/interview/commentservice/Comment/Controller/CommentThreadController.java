package com.intuit.interview.commentservice.Comment.Controller;

import com.intuit.interview.commentservice.Comment.Repository.CommentThreadRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("commentThread")
public class CommentThreadController {

    private final CommentThreadRepository commentThreadRepository;
    CommentThreadController(CommentThreadRepository commentThreadRepository)
    {
        this.commentThreadRepository = commentThreadRepository;
    }

    @GetMapping("/{id}")
    public List<BasicDBObject> threadDetails(@PathVariable("id") String commentThreadId)
    {
        // fetch details from user table and return
        return commentThreadRepository.commentThreadsForComment(commentThreadId, 0, 100);

    }
}

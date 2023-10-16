package com.intuit.interview.commentservice.Controller;

import com.intuit.interview.commentservice.Model.CommentThread;
import com.intuit.interview.commentservice.Model.CommentThreadWithComment;
import com.intuit.interview.commentservice.Model.User;
import com.intuit.interview.commentservice.Repositories.CommentThreadRepository;
import com.intuit.interview.commentservice.Service.CommentService;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
        return commentThreadRepository.commentThreadData(commentThreadId);

    }
}

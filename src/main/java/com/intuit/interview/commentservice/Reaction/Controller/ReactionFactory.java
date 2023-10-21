package com.intuit.interview.commentservice.Reaction.Controller;

import com.intuit.interview.commentservice.Comment.Service.CommentService;
import com.intuit.interview.commentservice.Constants.EntityType;
import com.intuit.interview.commentservice.EntityService;
import com.intuit.interview.commentservice.Post.Service.PostService;
import org.springframework.stereotype.Component;

@Component
public class ReactionFactory {

    private final PostService postService;
    private final CommentService commentService;

    ReactionFactory(PostService postService, CommentService commentService)
    {
        this.commentService = commentService;
        this.postService = postService;
    }

    public EntityService getInstance(String entityType)
    {
        if(entityType.equals(EntityType.POST.toString()))
            return postService;
        return commentService;
    }
}

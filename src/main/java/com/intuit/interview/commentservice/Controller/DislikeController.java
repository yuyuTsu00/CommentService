package com.intuit.interview.commentservice.Controller;

import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Constants.EntityType;
import com.intuit.interview.commentservice.Model.Reaction;
import com.intuit.interview.commentservice.Repositories.ReactionRepository;
import com.intuit.interview.commentservice.Service.CommentService;
import com.intuit.interview.commentservice.Service.PostService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dislike")
public class DislikeController implements ReactionController {
    private final ReactionRepository reactionRepository;
    private final PostService postService;
    private final CommentService commentService;
    DislikeController(ReactionRepository reactionRepository, PostService postService, CommentService commentService)
    {
        this.reactionRepository = reactionRepository;
        this.commentService = commentService;
        this.postService = postService;
    }

    @Override
    public void doneReaction(Reaction reaction)
    {
        if(reaction.getEntityType().equals(EntityType.POST.toString()))
            postService.handleDislike(reaction);
        else
            commentService.handleDislike(reaction);

        reaction.setReactionType(Emotion.DISLIKE.toString());
        reactionRepository.save(reaction);
    }

    @Override
    public void undoneReaction(Reaction reaction) {
        if(reaction.getEntityType().equals(EntityType.POST.toString()))
            postService.handleUndoDislike(reaction);
        else
            commentService.handleDislike(reaction);
        Reaction dbReaction = reactionRepository.getReactionByIds(reaction.getEntityId(), reaction.getUserId());
        if(dbReaction != null)
            reactionRepository.delete(dbReaction);
    }
}
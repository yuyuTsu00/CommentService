package com.intuit.interview.commentservice.Reaction.Controller;

import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Entity.EntityService;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.intuit.interview.commentservice.Reaction.Repository.ReactionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dislike")
public class DislikeController implements ReactionController {
    private final ReactionRepository reactionRepository;
    private final ReactionFactory reactionFactory;

    DislikeController(ReactionRepository reactionRepository, ReactionFactory reactionFactory)
    {
        this.reactionRepository = reactionRepository;
        this.reactionFactory = reactionFactory;
    }

    @Override
    public void doneReaction(Reaction reaction)
    {
        EntityService entityService = reactionFactory.getInstance(reaction.getEntityType());
        entityService.handleDislike(reaction);

        reaction.setReactionType(Emotion.DISLIKE.toString());
        reactionRepository.save(reaction);
    }

    @Override
    public void undoneReaction(Reaction reaction) {
        EntityService entityService = reactionFactory.getInstance(reaction.getEntityType());
        entityService.handleUndoDislike(reaction);
        Reaction dbReaction = reactionRepository.getReactionByIds(reaction.getEntityId(), reaction.getUserId());
        if(dbReaction != null)
            reactionRepository.delete(dbReaction);
    }
}
package com.intuit.interview.commentservice.Reaction.Service;

import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Entity.EntityService;
import com.intuit.interview.commentservice.Reaction.Controller.ReactionFactory;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.intuit.interview.commentservice.Reaction.Repository.ReactionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReactionServiceImpl implements ReactionService{

    private final ReactionRepository reactionRepository;
    private final ReactionFactory reactionFactory;

    ReactionServiceImpl(ReactionRepository reactionRepository, ReactionFactory reactionFactory)
    {
        this.reactionRepository = reactionRepository;
        this.reactionFactory = reactionFactory;
    }
    @Override
    public void doneReaction(Reaction reaction, Emotion emotion)
    {
        Optional<Reaction> dbReaction = reactionRepository.getReactionByIds(reaction.getEntityId(), reaction.getUserId(), reaction.getEntityType(), emotion.name());

        if(dbReaction.isPresent())
            return;

        EntityService entityService = reactionFactory.getInstance(reaction.getEntityType());

        switch (emotion){
            case LIKE -> entityService.handleLike(reaction);
            case DISLIKE -> entityService.handleDislike(reaction);
        }

        reaction.setReactionType(emotion.toString());
        reactionRepository.save(reaction);
    }

    @Override
    public void undoneReaction(Reaction reaction, Emotion emotion) {

        Optional<Reaction> dbReaction = reactionRepository.getReactionByIds(reaction.getEntityId(), reaction.getUserId(), reaction.getEntityType(), emotion.name());

        if(dbReaction.isEmpty())
            return;

        EntityService entityService = reactionFactory.getInstance(reaction.getEntityType());

        switch (emotion){
            case LIKE -> entityService.handleUndoLike(reaction);
            case DISLIKE -> entityService.handleUndoDislike(reaction);
        }


        reactionRepository.delete(dbReaction.get());
    }
}

package com.intuit.interview.commentservice.Reaction.Service;

import com.intuit.interview.commentservice.CommonUtility.PaginatedResponse;
import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Entity.EntityService;
import com.intuit.interview.commentservice.Reaction.Controller.ReactionFactory;
import com.intuit.interview.commentservice.Reaction.DTO.ReactionDto;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.intuit.interview.commentservice.Reaction.Repository.ReactionRepository;
import com.mongodb.BasicDBObject;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final ReactionFactory reactionFactory;

    ReactionServiceImpl(ReactionRepository reactionRepository, ReactionFactory reactionFactory) {
        this.reactionRepository = reactionRepository;
        this.reactionFactory = reactionFactory;
    }

    @Override
    public void doneReaction(ReactionDto reaction, Emotion emotion) {
        Optional<Reaction> dbReaction =
                reactionRepository.getReactionByIds(
                        reaction.getEntityId(), reaction.getUserId(), reaction.getEntityType(), emotion.name());

        if (dbReaction.isPresent()) return;

        Reaction reactionEntity = reactionDtoToReaction(reaction);
        EntityService entityService = reactionFactory.getInstance(reactionEntity.getEntityType());

        switch (emotion) {
            case LIKE -> entityService.handleLike(reactionEntity);
            case DISLIKE -> entityService.handleDislike(reactionEntity);
        }

        reactionEntity.setReactionType(emotion.toString());
        reactionRepository.save(reactionEntity);
    }

    @Override
    public void undoneReaction(ReactionDto reaction, Emotion emotion) {

        Optional<Reaction> dbReaction =
                reactionRepository.getReactionByIds(
                        reaction.getEntityId(), reaction.getUserId(), reaction.getEntityType(), emotion.name());

        if (dbReaction.isEmpty()) return;

        Reaction reactionEntity = reactionDtoToReaction(reaction);
        EntityService entityService = reactionFactory.getInstance(reaction.getEntityType());

        switch (emotion) {
            case LIKE -> entityService.handleUndoLike(reactionEntity);
            case DISLIKE -> entityService.handleUndoDislike(reactionEntity);
        }

        reactionRepository.delete(dbReaction.get());
    }

    public PaginatedResponse<BasicDBObject> getAllReactions(
            String entityId, String emotion, Pageable pageable) {
        List<BasicDBObject> list = reactionRepository.getReactionOfEntity(entityId, emotion, pageable);
        return PaginatedResponse.<BasicDBObject>builder()
                .items(list)
                .start(pageable.getPageNumber() + 1)
                .count(list.size())
                .build();
    }

    private Reaction reactionDtoToReaction(ReactionDto reactionDto) {
        Reaction reaction = new Reaction();
        reaction.setUserId(reactionDto.getUserId());
        reaction.setEntityType(reactionDto.getEntityType());
        reaction.setEntityId(reactionDto.getEntityId());

        return reaction;
    }
}

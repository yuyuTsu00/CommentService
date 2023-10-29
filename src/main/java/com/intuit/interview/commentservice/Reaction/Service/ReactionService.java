package com.intuit.interview.commentservice.Reaction.Service;

import com.intuit.interview.commentservice.CommonUtility.PaginatedResponse;
import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Reaction.DTO.ReactionDto;
import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;

public interface ReactionService {

    void doneReaction(ReactionDto reaction, Emotion emotion);

    void undoneReaction(ReactionDto reaction, Emotion emotion);

    PaginatedResponse<BasicDBObject> getAllReactions(
            String entityId, String reactionType, Pageable pageable);
}

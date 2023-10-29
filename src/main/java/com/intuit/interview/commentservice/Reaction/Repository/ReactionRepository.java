package com.intuit.interview.commentservice.Reaction.Repository;

import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionRepository extends MongoRepository<Reaction, String> {

    @Query("{ entityId: '?0', userId: '?1', entityType: '?2', reactionType: '?3' }")
    Optional<Reaction> getReactionByIds(String entityId, String userId, String entityType, String reactionType);
}
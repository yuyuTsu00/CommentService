package com.intuit.interview.commentservice.Reaction.Repository;

import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ReactionRepository extends MongoRepository<Reaction, String> {

    @Query("{ entityId: '?0', userId: '?1' }")
    public Reaction getReactionByIds(String entityId, String userId);
}
package com.intuit.interview.commentservice.Reaction.Repository;

import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.mongodb.BasicDBObject;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends MongoRepository<Reaction, String> {

    @Query("{ entityId: '?0', userId: '?1', entityType: '?2', reactionType: '?3' }")
    Optional<Reaction> getReactionByIds(
            String entityId, String userId, String entityType, String reactionType);

    @Aggregation(
            pipeline = {
                "{$lookup:{from: 'user',localField: 'userId',foreignField: '_id', as: 'userobject'}}",
                "{$match:   { entityId: '?0', reactionType: '?1' }}"
            })
    List<BasicDBObject> getReactionOfEntity(String entityId, String reactionType, Pageable pageable);
}

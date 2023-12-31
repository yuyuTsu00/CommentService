package com.intuit.interview.commentservice.Comment.Repository;

import com.intuit.interview.commentservice.Comment.Model.CommentThread;
import com.mongodb.BasicDBObject;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

// "{$sort: { startedOn : -1 }}"
public interface CommentThreadRepository extends MongoRepository<CommentThread, String> {

    @Aggregation(
            pipeline = {
                "{$lookup:{from: 'comment',localField: 'commentId',foreignField: '_id', as:"
                        + " 'commentobject'}}",
                "{$lookup:{from: 'user',localField: 'userId',foreignField: '_id', as: 'userobject'}}",
                "{$match:   {postId: '?0', isActive:  true, parentThreadId:  null}}"
            })
    List<BasicDBObject> commentThreadsForPost(String postId, Pageable pageable);

    @Aggregation(
            pipeline = {
                "{$lookup:{from: 'comment',localField: 'commentId',foreignField: '_id', as:"
                        + " 'commentobject'}}",
                "{$lookup:{from: 'user',localField: 'userId',foreignField: '_id', as: 'userobject'}}",
                "{$match:   {parentThreadId: '?0', isActive:  true}}"
            })
    List<BasicDBObject> commentThreadsForComment(String parentCommentThreadId, Pageable pageable);
}

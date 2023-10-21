package com.intuit.interview.commentservice.Comment.Repository;

import com.intuit.interview.commentservice.Comment.Model.CommentThread;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentThreadRepository extends MongoRepository<CommentThread, String> {

    @Aggregation(pipeline = {
            "{$lookup:{from: 'comment',localField: 'commentId',foreignField: '_id', as: 'commentobject'}}",
            "{$lookup:{from: 'user',localField: 'userId',foreignField: '_id', as: 'userobject'}}",
            "{$match:   {postId: '?0'}}",
            "{$sort: { startedOn : -1 }}",
            "{$skip: ?1}",
            "{$limit: ?2}"
    })
    List<BasicDBObject> commentThreadsForPost(String postId, int start, int limit);


    @Aggregation(pipeline = {
            "{$lookup:{from: 'comment',localField: 'commentId',foreignField: '_id', as: 'commentobject'}}",
            "{$lookup:{from: 'user',localField: 'userId',foreignField: '_id', as: 'userobject'}}",
            "{$match:   {parentCommentThreadId: '?0'}}",
            "{$orderby: { startedOn : -1 }}",
            "{$skip: ?1}",
            "{$limit: ?2}"
    })
    List<BasicDBObject> commentThreadsForComment(String parentCommentThreadId, int start, int limit);
}

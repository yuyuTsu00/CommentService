package com.intuit.interview.commentservice.Comment.Repository;

import com.intuit.interview.commentservice.Comment.Model.CommentThread;
import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentThreadRepository extends MongoRepository<CommentThread, String> {

    @Aggregation(pipeline = {
            "{$lookup:{from: 'comment',localField: 'commentId',foreignField: '_id', as: 'commentobject'}}",
            "{$lookup:{from: 'user',localField: 'commentobject.userId',foreignField: '_id', as: 'userobject'}}",
            "{$match:   {postId: '?0'}}",
            "{$sort: { startedOn : -1 }}"
    })
    List<BasicDBObject> commentThreadsForPost(String postId, Pageable pageable);


    @Aggregation(pipeline = {
            "{$lookup:{from: 'comment',localField: 'commentId',foreignField: '_id', as: 'commentobject'}}",
            "{$lookup:{from: 'user',localField: 'commentobject.userId',foreignField: '_id', as: 'userobject'}}",
            "{$match:   {parentCommentThreadId: '?0'}}",
            "{$orderby: { startedOn : -1 }}"
    })
    List<BasicDBObject> commentThreadsForComment(String parentCommentThreadId, Pageable pageable);
}

package com.intuit.interview.commentservice.Repositories;

import com.intuit.interview.commentservice.Model.CommentThread;
import com.mongodb.BasicDBObject;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentThreadRepository extends MongoRepository<CommentThread, String> {

    @Aggregation(pipeline = {
            "{$lookup:{from: 'comment',localField: 'commentId',foreignField: '_id', as: 'commentobject',}}",
            "{$match:   {parentCommentThreadId: '?0'}}"
    })
    List<BasicDBObject> commentThreadData(String parentCommentThreadId);
}

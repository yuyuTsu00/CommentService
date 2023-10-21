package com.intuit.interview.commentservice.Comment.Service;

import com.intuit.interview.commentservice.Comment.Exception.CommentNotFoundException;
import com.intuit.interview.commentservice.Comment.Model.Comment;
import com.intuit.interview.commentservice.Comment.Model.CommentThread;
import com.intuit.interview.commentservice.EntityService;
import com.intuit.interview.commentservice.PaginatedResponse;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CommentService extends EntityService {

    public Comment commentDetails(String commentId) throws CommentNotFoundException;
    public Comment deleteComment(String commentId) throws CommentNotFoundException;
    public Comment addComment(Comment comment);
    public Comment updateComment(Comment comment) throws CommentNotFoundException;
    public PaginatedResponse<BasicDBObject> commentThreadsForPost(String postId, int start);
    public PaginatedResponse<BasicDBObject> commentThreadsForComment(String threadId, int start);
}

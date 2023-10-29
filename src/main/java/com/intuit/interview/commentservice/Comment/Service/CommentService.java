package com.intuit.interview.commentservice.Comment.Service;

import com.intuit.interview.commentservice.Comment.DTO.NewCommentDto;
import com.intuit.interview.commentservice.Comment.Exception.CommentNotFoundException;
import com.intuit.interview.commentservice.Comment.Model.Comment;
import com.intuit.interview.commentservice.Comment.Model.CommentThread;
import com.intuit.interview.commentservice.Entity.EntityService;
import com.intuit.interview.commentservice.CommonUtility.PaginatedResponse;
import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;

public interface CommentService extends EntityService {

    Comment commentDetails(String commentId) throws CommentNotFoundException;
    CommentThread deleteComment(String threadId) throws CommentNotFoundException;
    Comment addComment(NewCommentDto comment, String postId, String userId, String threadId);
    Comment updateComment(Comment comment) throws CommentNotFoundException;
    PaginatedResponse<BasicDBObject> commentThreadsForPost(String postId, Pageable pageable);
    PaginatedResponse<BasicDBObject> commentThreadsForComment(String threadId, Pageable pageable);
}

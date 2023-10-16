package com.intuit.interview.commentservice.Model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class CommentThreadWithComment {

    @MongoId
    String commentThreadId;

    String commentId;

    String parentCommentThreadId = null;

    Comment comment;

    List<CommentThread> commentThreads;

    boolean isActive = true;
}

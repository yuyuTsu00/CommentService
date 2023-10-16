package com.intuit.interview.commentservice.Model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "commentThread")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentThread {

    @MongoId
    String commentThreadId;

    String commentId;

    String parentCommentThreadId = null;

    List<CommentThread> commentThreads;

    boolean isActive = true;
}

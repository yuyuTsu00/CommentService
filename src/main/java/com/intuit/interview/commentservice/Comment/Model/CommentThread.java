package com.intuit.interview.commentservice.Comment.Model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.Date;
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
//    List<CommentThread> commentThreads;
    String userId;
    String postId;
    Date startedOn = Date.from(Instant.now());
    Date lastUpdatedON = Date.from(Instant.now());
    boolean isActive = true;
}

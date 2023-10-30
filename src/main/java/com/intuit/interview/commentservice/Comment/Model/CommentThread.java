package com.intuit.interview.commentservice.Comment.Model;

import java.time.Instant;
import java.util.Date;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "commentThread")
@Getter
@Setter
@ToString
public class CommentThread {
    @MongoId private String threadId;
    private String commentId;
    private String parentThreadId;
    private String postId;
    private String userId;
    private Date startedOn = Date.from(Instant.now());
    private Date lastUpdatedON = Date.from(Instant.now());
    private boolean isActive = true;
}

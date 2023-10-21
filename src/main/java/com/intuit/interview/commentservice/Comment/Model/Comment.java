package com.intuit.interview.commentservice.Comment.Model;

import com.intuit.interview.commentservice.Constants.EntityType;
import com.intuit.interview.commentservice.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.Date;

@Document(collection = "comment")
@Getter
@Setter
@ToString
public class Comment implements Entity {
    @MongoId
    String commentId;
    String message;
    String threadId;
    String postId;
    String userId;
    int likeCounter = 0;
    int dislikeCounter = 0;
    String entityType = EntityType.COMMENT.toString();
    Date postedOn = Date.from(Instant.now());
    Date lastUpdatedON = Date.from(Instant.now());
}

package com.intuit.interview.commentservice.Model;

import com.intuit.interview.commentservice.Constants.EntityType;
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
    int likeCounter = 0;
    int dislikeCounter = 0;
    String userId;
    String postId;
    String entityType = EntityType.COMMENT.toString();
    String parentCommentThreadId = null;
    Date postedOn = Date.from(Instant.now());
    Date lastUpdatedON = Date.from(Instant.now());
    boolean isActive = true;
}

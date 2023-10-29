package com.intuit.interview.commentservice.Comment.Model;

import com.intuit.interview.commentservice.Constants.EntityType;
import com.intuit.interview.commentservice.Entity.Entity;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "comment")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comment implements Entity, Serializable {
    @MongoId private String commentId;
    private String message;
    private int likeCounter = 0;
    private int dislikeCounter = 0;
    private String entityType = EntityType.COMMENT.toString();
    private Date postedOn = Date.from(Instant.now());
    private Date lastUpdatedON = Date.from(Instant.now());
}

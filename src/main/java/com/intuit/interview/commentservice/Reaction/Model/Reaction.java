package com.intuit.interview.commentservice.Reaction.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Document(collection = "reaction")
@Getter
@Setter
@ToString
public class Reaction {
    @MongoId
    private String reactionId;
    private String userId;
    private String entityType;
    private String entityId;
    private Date reactionTime = Date.from(Instant.now());
    private String reactionType;
}

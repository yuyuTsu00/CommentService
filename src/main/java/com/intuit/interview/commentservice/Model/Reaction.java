package com.intuit.interview.commentservice.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Document(collection = "reaction")
@Getter
@Setter
@ToString
public class Reaction {
    @MongoId
    String reactionId;
    String userId;
    String entityType;
    String entityId;
    Timestamp reactionTime;
    String reactionType;
}

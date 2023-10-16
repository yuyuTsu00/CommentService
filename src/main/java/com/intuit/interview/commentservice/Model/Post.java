package com.intuit.interview.commentservice.Model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Document(collection = "post")
@Getter
@Setter
@ToString
public class Post {

    @MongoId
    String postId;


    String userId;


    Date postedOn = Date.from(Instant.now());
    Date lastUpdatedON = Date.from(Instant.now());

    int likeCounter = 0;
    int dislikeCounter = 0;

    String postMessage;
    String shareLink;

    boolean isActive = true;
}

package com.intuit.interview.commentservice.Post.Model;

import com.intuit.interview.commentservice.Constants.EntityType;
import com.intuit.interview.commentservice.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Document(collection = "post")
@Getter
@Setter
@ToString
public class Post implements Entity, Serializable {

    @MongoId
    String postId;
    String userId;
    Date postedOn = Date.from(Instant.now());
    Date lastUpdatedON = Date.from(Instant.now());
    int likeCounter = 0;
    int dislikeCounter = 0;
    String postMessage;
    String shareLink;
    String entityType = EntityType.POST.toString();

    boolean isActive = true;
}
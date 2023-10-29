package com.intuit.interview.commentservice.Post.Model;

import com.intuit.interview.commentservice.Constants.EntityType;
import com.intuit.interview.commentservice.Entity.Entity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Document(collection = "post")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Post implements Entity, Serializable {

    @MongoId
    private String postId;
    private String userId;
    private String postMessage;

    private Date postedOn = Date.from(Instant.now());
    private Date lastUpdatedON = Date.from(Instant.now());

    private int likeCounter = 0;
    private int dislikeCounter = 0;
    private String shareLink;
    private String entityType = EntityType.POST.toString();
    private boolean isActive = true;
}

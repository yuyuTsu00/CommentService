package com.intuit.interview.commentservice.User.Model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @MongoId
    private String userId;
    private String userName;
    private boolean isActive = true;
    private String profileUrl;
}

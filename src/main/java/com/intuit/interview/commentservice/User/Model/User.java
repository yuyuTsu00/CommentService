package com.intuit.interview.commentservice.User.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "user")
@Getter
@Setter
@ToString
public class User {

    @MongoId
    String userId;
    String userName;
}

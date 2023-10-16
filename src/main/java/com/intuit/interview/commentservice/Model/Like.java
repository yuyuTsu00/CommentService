package com.intuit.interview.commentservice.Model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Document
public class Like {
    User user;
    int entityId;
    Timestamp likedOn;
}

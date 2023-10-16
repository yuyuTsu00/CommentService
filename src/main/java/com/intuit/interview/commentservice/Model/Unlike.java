package com.intuit.interview.commentservice.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document
public class Unlike {
    User user;
    int entityId;
    Timestamp unlikedOn;
}

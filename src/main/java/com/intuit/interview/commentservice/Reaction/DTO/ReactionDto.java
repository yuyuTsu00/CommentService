package com.intuit.interview.commentservice.Reaction.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReactionDto {
    private String userId;
    private String entityType;
    private String entityId;
}

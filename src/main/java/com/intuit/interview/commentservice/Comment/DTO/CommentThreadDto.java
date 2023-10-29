package com.intuit.interview.commentservice.Comment.DTO;

import com.intuit.interview.commentservice.Constants.EntityType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentThreadDto {
    private String threadId;
    private String postId;
    private String userId;
    private String commentId;
    private String message;
    private int likeCounter;
    private int dislikeCounter;
    private String entityType = EntityType.COMMENT.toString();
    private String userName;
}


package com.intuit.interview.commentservice.Comment.Exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String message) {
        super(message);
    }
    public CommentNotFoundException() {
        super();
    }
}
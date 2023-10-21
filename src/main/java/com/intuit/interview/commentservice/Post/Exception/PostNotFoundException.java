package com.intuit.interview.commentservice.Post.Exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message) {
        super(message);
    }
    public PostNotFoundException() {
        super();
    }
}
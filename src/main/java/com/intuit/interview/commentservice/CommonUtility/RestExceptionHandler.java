package com.intuit.interview.commentservice.CommonUtility;

import com.intuit.interview.commentservice.Comment.Exception.CommentNotFoundException;
import com.intuit.interview.commentservice.Post.Exception.PostNotFoundException;
import com.intuit.interview.commentservice.User.Exception.UserNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFoundException.class)
    protected ResponseEntity<Object> handlePostNotFound(PostNotFoundException ex) {
        return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    protected ResponseEntity<Object> handleCommentNotFound(CommentNotFoundException ex) {
        return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
    }
}
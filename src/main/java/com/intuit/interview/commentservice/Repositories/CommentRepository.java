package com.intuit.interview.commentservice.Repositories;

import com.intuit.interview.commentservice.Model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}

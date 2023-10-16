package com.intuit.interview.commentservice.Repositories;

import com.intuit.interview.commentservice.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

package com.intuit.interview.commentservice.User.Repository;

import com.intuit.interview.commentservice.User.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

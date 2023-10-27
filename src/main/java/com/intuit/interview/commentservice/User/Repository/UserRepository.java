package com.intuit.interview.commentservice.User.Repository;

import com.intuit.interview.commentservice.User.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{isActive:  true}")
    List<User> getActiveUsers();
}

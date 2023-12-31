package com.intuit.interview.commentservice.Post.Repository;

import com.intuit.interview.commentservice.Post.Model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    @Query("{userId:  '?0', isActive: true}")
    List<Post> getAllPostOfUser(String userId, Pageable pageable);
}

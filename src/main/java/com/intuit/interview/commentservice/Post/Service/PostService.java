package com.intuit.interview.commentservice.Post.Service;

import com.intuit.interview.commentservice.Entity.EntityService;
import com.intuit.interview.commentservice.CommonUtility.PaginatedResponse;
import com.intuit.interview.commentservice.Post.DTO.NewPostDto;
import com.intuit.interview.commentservice.Post.Exception.PostNotFoundException;
import com.intuit.interview.commentservice.Post.Model.Post;
import org.springframework.data.domain.Pageable;

public interface PostService extends EntityService {
    Post postDetails(String postId) throws PostNotFoundException;
    Post deletePost(String postId) throws PostNotFoundException;
    Post newPost(NewPostDto post);
    Post updatePost(Post post) throws PostNotFoundException;
    PaginatedResponse<Post> getAllPostOfUser(String userId, Pageable pageable);
}

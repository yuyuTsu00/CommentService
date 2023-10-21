package com.intuit.interview.commentservice.Post.Service;

import com.intuit.interview.commentservice.EntityService;
import com.intuit.interview.commentservice.Post.Exception.PostNotFoundException;
import com.intuit.interview.commentservice.Post.Model.Post;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;

import java.util.List;

public interface PostService extends EntityService {
    public Post postDetails(String postId) throws PostNotFoundException;
    public Post deletePost(String postId) throws PostNotFoundException;
    public Post newPost(Post post);
    public Post updatePost(Post post) throws PostNotFoundException;
    public List<Post> getAllPostOfUser(String userId);
}

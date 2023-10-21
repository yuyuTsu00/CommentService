package com.intuit.interview.commentservice.Post.Service;

import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Post.Exception.PostNotFoundException;
import com.intuit.interview.commentservice.Post.Model.Post;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.intuit.interview.commentservice.Post.Repository.PostRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableCaching
public class PostServiceImpl implements PostService
{
    private final PostRepository postRepository;
    PostServiceImpl(PostRepository postRepository)
    {
        this.postRepository = postRepository;
    }

    @Cacheable("post")
    public Post postDetails(String postId) throws PostNotFoundException
    {
        // fetch details from post table and return
        Optional<Post> post = postRepository.findById(postId);

        if(post.isEmpty())
            throw new PostNotFoundException();

        return post.get();
    }

    @CacheEvict("post")
    public Post deletePost(String postId) throws PostNotFoundException
    {
        // remove post from table with given id
        Optional<Post> post = postRepository.findById(postId);
        System.out.println(post);

        if(post.isEmpty())
            throw new PostNotFoundException();

        postRepository.delete(post.get());
        return post.get();
    }

    public Post newPost(Post post)
    {
        // insert new post in the post table
        return postRepository.insert(post);
    }

    @CachePut("post")
    public Post updatePost( Post post) throws PostNotFoundException
    {
        // update the already present post
        Optional<Post> dbpost = postRepository.findById(post.getPostId());
        System.out.println(post);

        if(dbpost.isEmpty())
            throw new PostNotFoundException();

        dbpost.get().setLastUpdatedON(Date.from(Instant.now()));
        dbpost.get().setPostMessage(post.getPostMessage());
        postRepository.save(dbpost.get());
        return dbpost.get();
    }

    public List<Post> getAllPostOfUser(String userId)
    {
        return postRepository.getAllPostOfUser(userId);
    }

    public void handleLike(Reaction reaction)
    {
        handleAction(reaction, 1, Emotion.LIKE);
    }

    public void handleUndoLike(Reaction reaction)
    {
        handleAction(reaction, -1, Emotion.LIKE);
    }

    public void handleDislike(Reaction reaction)
    {
        handleAction(reaction, 1, Emotion.DISLIKE);
    }

    public void handleUndoDislike(Reaction reaction)
    {
        handleAction(reaction, -1, Emotion.DISLIKE);
    }

    private void handleAction(Reaction reaction, int change, Emotion emotion)
    {
        Optional<Post> post = postRepository.findById(reaction.getEntityId());
        System.out.println(post);
        if(post.isPresent())
        {
            switch (emotion){
                case LIKE : {
                    post.get().setLikeCounter(post.get().getLikeCounter() + change);
                    break;
                }
                case DISLIKE : {
                    post.get().setDislikeCounter(post.get().getDislikeCounter() + change);
                    break;
                }
            }
            post.get().setLikeCounter(post.get().getLikeCounter() + change);
            postRepository.save(post.get());
        }
    }
}

package com.intuit.interview.commentservice.Post.Service;

import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.CommonUtility.PaginatedResponse;
import com.intuit.interview.commentservice.Post.DTO.NewPostDto;
import com.intuit.interview.commentservice.Post.Exception.PostNotFoundException;
import com.intuit.interview.commentservice.Post.Model.Post;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.intuit.interview.commentservice.Post.Repository.PostRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

        post.get().setActive(false);
        postRepository.save(post.get());
        return post.get();
    }

    public Post newPost(NewPostDto post)
    {
        // insert new post in the post table
        Post newPost = new Post();
        newPost.setPostMessage(post.getMessage());
        newPost.setUserId(post.getUserId());
        return postRepository.insert(newPost);
    }

    @CachePut("post")
    public Post updatePost(Post post) throws PostNotFoundException
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

    public PaginatedResponse<Post> getAllPostOfUser(String userId, Pageable pageable)
    {
        List<Post> posts = postRepository.getAllPostOfUser(userId, pageable);
        return PaginatedResponse.<Post>builder().items(posts).start(pageable.getPageNumber() + 1).count(posts.size()).build();
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
        if(post.isPresent())
        {
            switch (emotion){
                case LIKE -> post.get().setLikeCounter(post.get().getLikeCounter() + change);
                case DISLIKE -> post.get().setDislikeCounter(post.get().getDislikeCounter() + change);

            }
            postRepository.save(post.get());
        }
    }
}

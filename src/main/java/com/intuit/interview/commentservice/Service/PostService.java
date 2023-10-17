package com.intuit.interview.commentservice.Service;

import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Constants.EntityType;
import com.intuit.interview.commentservice.Model.Post;
import com.intuit.interview.commentservice.Model.Reaction;
import com.intuit.interview.commentservice.Repositories.PostRepository;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    PostService(PostRepository postRepository)
    {
        this.postRepository = postRepository;
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
                case LIKE -> post.get().setLikeCounter(post.get().getLikeCounter() + change);
                case DISLIKE -> post.get().setDislikeCounter(post.get().getDislikeCounter() + change);
            }
            post.get().setLikeCounter(post.get().getLikeCounter() + change);
            postRepository.save(post.get());
        }
    }
}

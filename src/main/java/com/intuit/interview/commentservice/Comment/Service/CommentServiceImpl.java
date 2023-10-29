package com.intuit.interview.commentservice.Comment.Service;

import com.intuit.interview.commentservice.Comment.DTO.NewCommentDto;
import com.intuit.interview.commentservice.Comment.Exception.CommentNotFoundException;
import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Comment.Model.Comment;
import com.intuit.interview.commentservice.Comment.Model.CommentThread;
import com.intuit.interview.commentservice.CommonUtility.PaginatedResponse;
import com.intuit.interview.commentservice.Post.Model.Post;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.intuit.interview.commentservice.Comment.Repository.CommentRepository;
import com.intuit.interview.commentservice.Comment.Repository.CommentThreadRepository;
import com.mongodb.BasicDBObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentThreadRepository commentThreadRepository;
    private final CommentRepository commentRepository;

    CommentServiceImpl(CommentThreadRepository commentThreadRepository, CommentRepository commentRepository)
    {
        this.commentThreadRepository = commentThreadRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Cacheable("comment")
    public Comment commentDetails(String commentId) throws CommentNotFoundException {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if(comment.isEmpty())
            throw new CommentNotFoundException();
        return comment.get();
    }

    @Override
//    @CacheEvict(value = "paginatedComment", key = "#threadId")
    public CommentThread deleteComment(String threadId) throws CommentNotFoundException {
        Optional<CommentThread> commentThread = commentThreadRepository.findById(threadId);

        if(commentThread.isEmpty())
            throw new CommentNotFoundException();

        commentThread.get().setActive(false);
        commentThread.get().setLastUpdatedON(Date.from(Instant.now()));
        commentThreadRepository.save(commentThread.get());
        return commentThread.get();
    }

//    @Caching(evict = {
//            @CacheEvict(value = "paginatedPostComment", key = "#postId"),
//            @CacheEvict(value = "paginatedComment", key = "#threadId")
//    })
    public Comment addComment(NewCommentDto comment, String postId, String userId, String threadId)
    {
        Comment newComment = new Comment();
        newComment.setMessage(comment.getMessage());
        Comment insertedComment = commentRepository.insert(newComment);

        CommentThread commentThread = CommentThread.builder()
                                        .parentThreadId(threadId)
                                        .commentId(insertedComment.getCommentId())
                                        .postId(postId)
                                        .userId(userId)
                                        .isActive(true)
                                        .build();

        commentThreadRepository.insert(commentThread);
        return insertedComment;
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
        Optional<Comment> comment = commentRepository.findById(reaction.getEntityId());
        comment.ifPresent(value -> updateCounter(value, change, emotion));
    }

    @CachePut("comment")
    public void updateCounter(Comment comment, int change, Emotion emotion)
    {
        switch (emotion){
            case LIKE -> comment.setLikeCounter(comment.getLikeCounter() + change);
            case DISLIKE -> comment.setDislikeCounter(comment.getDislikeCounter() + change);

        }
        commentRepository.save(comment);
    }

    @CachePut("comment")
    public Comment updateComment(Comment comment) throws CommentNotFoundException
    {
        Optional<Comment> dbComment = commentRepository.findById(comment.getCommentId());
        System.out.println(dbComment);

        if(dbComment.isEmpty())
            throw new CommentNotFoundException();


        dbComment.get().setLastUpdatedON(Date.from(Instant.now()));
        dbComment.get().setMessage(comment.getMessage());
        commentRepository.save(dbComment.get());

        return dbComment.get();
    }

  //  @Cacheable(value = "paginatedPostComment", key = "#postId")
    public PaginatedResponse<BasicDBObject> commentThreadsForPost(String postId, Pageable pageable)
    {
        List<BasicDBObject> list = commentThreadRepository.commentThreadsForPost(postId, pageable);
        return PaginatedResponse.<BasicDBObject>builder().items(list).start(pageable.getPageNumber() + 1).count(list.size()).build();
    }

 //   @Cacheable(value = "paginatedComment", key = "#threadId")
    public PaginatedResponse<BasicDBObject> commentThreadsForComment(String threadId, Pageable pageable)
    {
        List<BasicDBObject> list = commentThreadRepository.commentThreadsForComment(threadId, pageable);
        return PaginatedResponse.<BasicDBObject>builder().items(list).start(pageable.getPageNumber()+1).count(list.size()).build();
    }
}

package com.intuit.interview.commentservice.Service;

import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Model.Comment;
import com.intuit.interview.commentservice.Model.CommentThread;
import com.intuit.interview.commentservice.Model.Post;
import com.intuit.interview.commentservice.Model.Reaction;
import com.intuit.interview.commentservice.Repositories.CommentRepository;
import com.intuit.interview.commentservice.Repositories.CommentThreadRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentThreadRepository commentThreadRepository;
    private final CommentRepository commentRepository;

    CommentService(CommentThreadRepository commentThreadRepository, CommentRepository commentRepository)
    {
        this.commentThreadRepository = commentThreadRepository;
        this.commentRepository = commentRepository;
    }

    public void addComment(Comment comment)
    {

        Comment insertedComment = commentRepository.insert(comment);
        CommentThread commentThread = new CommentThread();
        commentThread.setCommentId(insertedComment.getCommentId());
        commentThread.setCommentThreads(new ArrayList<>());
        if(comment.getParentCommentThreadId() != null)
            commentThread.setParentCommentThreadId(comment.getParentCommentThreadId());
        CommentThread insertedCommentThread = commentThreadRepository.insert(commentThread);
        System.out.println(insertedComment);
        System.out.println(insertedCommentThread);
        if(comment.getParentCommentThreadId() != null)
        {
            Optional<CommentThread> parentCommentThread = commentThreadRepository.findById(comment.getParentCommentThreadId());
            System.out.println(parentCommentThread);
            if(parentCommentThread.isPresent()) {
                parentCommentThread.get().getCommentThreads().add(insertedCommentThread);
                commentThreadRepository.save(parentCommentThread.get());
            }
        }
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
        System.out.println(comment);
        if(comment.isPresent())
        {
            switch (emotion){
                case LIKE -> comment.get().setLikeCounter(comment.get().getLikeCounter() + change);
                case DISLIKE -> comment.get().setDislikeCounter(comment.get().getDislikeCounter() + change);
            }
            comment.get().setLikeCounter(comment.get().getLikeCounter() + change);
            commentRepository.save(comment.get());
        }
    }
}

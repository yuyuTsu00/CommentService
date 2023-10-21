package com.intuit.interview.commentservice.Comment.Service;

import com.intuit.interview.commentservice.Comment.Exception.CommentNotFoundException;
import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Comment.Model.Comment;
import com.intuit.interview.commentservice.Comment.Model.CommentThread;
import com.intuit.interview.commentservice.PaginatedResponse;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.intuit.interview.commentservice.Comment.Repository.CommentRepository;
import com.intuit.interview.commentservice.Comment.Repository.CommentThreadRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
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
    public Comment commentDetails(String commentId) throws CommentNotFoundException {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if(comment.isEmpty())
            throw new CommentNotFoundException();
        return comment.get();
    }

    @Override
    public Comment deleteComment(String commentId) throws CommentNotFoundException {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if(comment.isEmpty())
            throw new CommentNotFoundException();

        commentRepository.delete(comment.get());
        return comment.get();
    }

    public Comment addComment(Comment comment)
    {
        Comment insertedComment = commentRepository.insert(comment);

        CommentThread commentThread = new CommentThread();
        commentThread.setCommentId(insertedComment.getCommentId());
        commentThread.setUserId(comment.getUserId());
        commentThread.setPostId(comment.getPostId());
//        commentThread.setCommentThreads(new ArrayList<>());
        if(comment.getThreadId() != null)
            commentThread.setParentCommentThreadId(comment.getThreadId());

        CommentThread insertedCommentThread = commentThreadRepository.insert(commentThread);
        System.out.println(insertedComment);
        System.out.println(insertedCommentThread);
//        if(comment.getParentCommentThreadId() != null)
//        {
//            Optional<CommentThread> parentCommentThread = commentThreadRepository.findById(comment.getParentCommentThreadId());
//            System.out.println(parentCommentThread);
//            if(parentCommentThread.isPresent()) {
//                parentCommentThread.get().getCommentThreads().add(insertedCommentThread);
//                commentThreadRepository.save(parentCommentThread.get());
//            }
//        }

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
        System.out.println(comment);
        if(comment.isPresent())
        {
            switch (emotion){
                case LIKE : {
                    comment.get().setLikeCounter(comment.get().getLikeCounter() + change);
                    break;
                }
                case DISLIKE : {
                    comment.get().setDislikeCounter(comment.get().getDislikeCounter() + change);
                    break;
                }
            }
            comment.get().setLikeCounter(comment.get().getLikeCounter() + change);
            commentRepository.save(comment.get());
        }
    }

    public Comment updateComment(Comment comment) throws CommentNotFoundException
    {
        Optional<Comment> dbcomment = commentRepository.findById(comment.getCommentId());
        System.out.println(dbcomment);

        if(dbcomment.isEmpty())
            throw new CommentNotFoundException();


        dbcomment.get().setLastUpdatedON(Date.from(Instant.now()));
        dbcomment.get().setMessage(comment.getMessage());
        commentRepository.save(dbcomment.get());

        return dbcomment.get();
    }

    public PaginatedResponse<BasicDBObject> commentThreadsForPost(String postId, int start)
    {
        List<BasicDBObject> list = commentThreadRepository.commentThreadsForPost(postId, start, 100);
        return PaginatedResponse.<BasicDBObject>builder().items(list).start(start+1).count(list.size()).build();
    }

    public PaginatedResponse<BasicDBObject> commentThreadsForComment(String threadId, int start)
    {
        List<BasicDBObject> list = commentThreadRepository.commentThreadsForComment(threadId, start, 100);
        return PaginatedResponse.<BasicDBObject>builder().items(list).start(start+1).count(list.size()).build();
    }
}

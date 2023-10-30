package com.intuit.interview.commentservice.Comment.Service;

import com.intuit.interview.commentservice.Comment.DTO.CommentThreadDto;
import com.intuit.interview.commentservice.Comment.DTO.NewCommentDto;
import com.intuit.interview.commentservice.Comment.Exception.CommentNotFoundException;
import com.intuit.interview.commentservice.Comment.Model.Comment;
import com.intuit.interview.commentservice.Comment.Model.CommentThread;
import com.intuit.interview.commentservice.Comment.Repository.CommentRepository;
import com.intuit.interview.commentservice.Comment.Repository.CommentThreadRepository;
import com.intuit.interview.commentservice.CommonUtility.PaginatedResponse;
import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.mongodb.BasicDBObject;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentThreadRepository commentThreadRepository;
    private final CommentRepository commentRepository;

    CommentServiceImpl(
            CommentThreadRepository commentThreadRepository, CommentRepository commentRepository) {
        this.commentThreadRepository = commentThreadRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Cacheable("comment")
    public Comment commentDetails(String commentId) throws CommentNotFoundException {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isEmpty()) throw new CommentNotFoundException();
        return comment.get();
    }

    @Override
    //    @CacheEvict(value = "paginatedComment", key = "#threadId")
    public CommentThread deleteComment(String threadId) throws CommentNotFoundException {
        Optional<CommentThread> commentThread = commentThreadRepository.findById(threadId);

        if (commentThread.isEmpty()) throw new CommentNotFoundException();

        commentThread.get().setActive(false);
        commentThread.get().setLastUpdatedON(Date.from(Instant.now()));
        commentThreadRepository.save(commentThread.get());
        return commentThread.get();
    }

    //    @Caching(evict = {
    //            @CacheEvict(value = "paginatedPostComment", key = "#postId"),
    //            @CacheEvict(value = "paginatedComment", key = "#threadId")
    //    })
    public Comment addComment(NewCommentDto comment, String postId, String userId, String threadId) {
        Comment newComment = new Comment();
        newComment.setMessage(comment.getMessage());
        Comment insertedComment = commentRepository.insert(newComment);

        CommentThread commentThread = new CommentThread();
        commentThread.setParentThreadId(threadId);
        commentThread.setCommentId(insertedComment.getCommentId());
        commentThread.setPostId(postId);
        commentThread.setUserId(userId);

        commentThreadRepository.insert(commentThread);
        return insertedComment;
    }

    public void handleLike(Reaction reaction) {
        handleAction(reaction, 1, Emotion.LIKE);
    }

    public void handleUndoLike(Reaction reaction) {
        handleAction(reaction, -1, Emotion.LIKE);
    }

    public void handleDislike(Reaction reaction) {
        handleAction(reaction, 1, Emotion.DISLIKE);
    }

    public void handleUndoDislike(Reaction reaction) {
        handleAction(reaction, -1, Emotion.DISLIKE);
    }

    private void handleAction(Reaction reaction, int change, Emotion emotion) {
        Optional<Comment> comment = commentRepository.findById(reaction.getEntityId());
        comment.ifPresent(value -> updateCounter(value, change, emotion));
    }

    @CachePut("comment")
    public void updateCounter(Comment comment, int change, Emotion emotion) {
        switch (emotion) {
            case LIKE -> comment.setLikeCounter(comment.getLikeCounter() + change);
            case DISLIKE -> comment.setDislikeCounter(comment.getDislikeCounter() + change);
        }
        commentRepository.save(comment);
    }

    @CachePut("comment")
    public Comment updateComment(Comment comment) throws CommentNotFoundException {
        Optional<Comment> dbComment = commentRepository.findById(comment.getCommentId());

        if (dbComment.isEmpty()) throw new CommentNotFoundException();

        dbComment.get().setLastUpdatedON(Date.from(Instant.now()));
        dbComment.get().setMessage(comment.getMessage());
        commentRepository.save(dbComment.get());

        return dbComment.get();
    }

    //  @Cacheable(value = "paginatedPostComment", key = "#postId")
    public PaginatedResponse<CommentThreadDto> commentThreadsForPost(
            String postId, Pageable pageable) {
        List<BasicDBObject> list = commentThreadRepository.commentThreadsForPost(postId, pageable);
        List<CommentThreadDto> responseList = dbObjectMapCommentThreadObject(list);
        return PaginatedResponse.<CommentThreadDto>builder()
                .items(responseList)
                .start(pageable.getPageNumber() + 1)
                .count(list.size())
                .build();
    }

    //   @Cacheable(value = "paginatedComment", key = "#threadId")
    public PaginatedResponse<CommentThreadDto> commentThreadsForComment(
            String threadId, Pageable pageable) {
        List<BasicDBObject> list = commentThreadRepository.commentThreadsForComment(threadId, pageable);
        List<CommentThreadDto> responseList = dbObjectMapCommentThreadObject(list);
        return PaginatedResponse.<CommentThreadDto>builder()
                .items(responseList)
                .start(pageable.getPageNumber() + 1)
                .count(list.size())
                .build();
    }

    private List<CommentThreadDto> dbObjectMapCommentThreadObject(List<BasicDBObject> list) {
        List<CommentThreadDto> result = new LinkedList<>();
        List<Document> docs = null;
        for (BasicDBObject object : list) {
            CommentThreadDto commentThreadDto = new CommentThreadDto();
            commentThreadDto.setThreadId(object.get("_id").toString());
            commentThreadDto.setPostId(object.get("postId").toString());
            commentThreadDto.setUserId(object.get("userId").toString());
            commentThreadDto.setCommentId(object.get("commentId").toString());

            docs = (List<Document>) object.get("commentobject");
            commentThreadDto.setMessage(docs.get(0).get("message").toString());
            commentThreadDto.setLikeCounter(Integer.parseInt(docs.get(0).get("likeCounter").toString()));
            commentThreadDto.setDislikeCounter(
                    Integer.parseInt(docs.get(0).get("dislikeCounter").toString()));

            docs = (List<Document>) object.get("userobject");
            commentThreadDto.setUserName(docs.get(0).get("userName").toString());

            result.add(commentThreadDto);
        }
        return result;
    }
}

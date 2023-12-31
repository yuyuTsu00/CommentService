package com.intuit.interview.commentservice;

import com.intuit.interview.commentservice.Comment.Model.Comment;
import com.intuit.interview.commentservice.Comment.Model.CommentThread;
import com.intuit.interview.commentservice.Comment.Repository.CommentRepository;
import com.intuit.interview.commentservice.Comment.Repository.CommentThreadRepository;
import com.intuit.interview.commentservice.Comment.Service.CommentServiceImpl;
import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CommentServiceApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentServiceTest {

    @Autowired private CommentRepository commentRepository;

    @Autowired private CommentThreadRepository commentThreadRepository;

    @Autowired private CommentServiceImpl commentService;
    private final String testPostId = "12345";
    private final String testUserId = "1234";
    private final String testCommentId = "xyz";
    private final String testThreadId = "abc";

    @Before
    public void init() {
        Comment comment = new Comment();
        comment.setCommentId(testCommentId);
        comment.setMessage("test comment");

        CommentThread commentThread = new CommentThread();
        commentThread.setPostId(testPostId);
        commentThread.setThreadId(testThreadId);
        commentThread.setParentThreadId(null);
        commentThread.setCommentId(testCommentId);
        commentThread.setUserId(testUserId);

        commentRepository.save(comment);
        commentThreadRepository.save(commentThread);
    }

    @After
    public void destroy() {
        commentRepository.deleteById(testCommentId);
        commentThreadRepository.deleteById(testThreadId);
    }

    @Test
    public void testCommentDetails() {
        Comment comment = commentService.commentDetails(testCommentId);
        Assert.assertEquals(comment.getCommentId(), testCommentId);
    }

    @AfterAll
    public void testCommentDelete() {
        CommentThread commentThread = commentService.deleteComment(testThreadId);
        Assert.assertFalse(commentThread.isActive());
    }
}

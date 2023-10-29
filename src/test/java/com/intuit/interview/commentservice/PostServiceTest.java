package com.intuit.interview.commentservice;

import com.intuit.interview.commentservice.CommonUtility.PaginatedResponse;
import com.intuit.interview.commentservice.Post.Model.Post;
import com.intuit.interview.commentservice.Post.Repository.PostRepository;
import com.intuit.interview.commentservice.Post.Service.PostServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CommentServiceApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostServiceTest {

    @Autowired private PostRepository postRepository;

    @Autowired private PostServiceImpl postService;
    private final String testPostId = "123456";
    private final String testUserId = "12345";

    @Before
    public void init() {
        Post post = new Post();
        post.setPostId(testPostId);
        post.setActive(true);
        post.setUserId(testUserId);

        postRepository.insert(post);
    }

    @After
    public void destroy() {
        Post post = postService.postDetails(testPostId);
        postRepository.delete(post);
    }

    @Test
    public void testPostDetails() {
        Post post = postService.postDetails(testPostId);
        Assert.assertEquals(post.getPostId(), testPostId);
    }

    @Test
    public void testGetPostOfUsers() {
        PaginatedResponse<Post> posts = postService.getAllPostOfUser(testUserId, PageRequest.of(0, 5));
        Assert.assertTrue(!posts.getItems().isEmpty());
    }

    @Test
    public void testDeleteUser() {
        Post post = postService.deletePost(testPostId);
        Assert.assertFalse(post.isActive());
    }
}

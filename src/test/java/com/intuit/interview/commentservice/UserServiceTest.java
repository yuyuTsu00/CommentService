package com.intuit.interview.commentservice;

import com.intuit.interview.commentservice.User.Model.User;
import com.intuit.interview.commentservice.User.Repository.UserRepository;
import com.intuit.interview.commentservice.User.Service.UserServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CommentServiceApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    private final String testUserId =  "123456";
    @Before
    public void init()
    {
        User user = new User();
        user.setUserName("abhishek");
        user.setUserId(testUserId);
        user.setActive(true);

        userRepository.insert(user);
    }

    @After
    public void destroy()
    {
        User user = userService.userDetails(testUserId);
        userRepository.delete(user);
    }

    @Test
    public void testUserDetails()
    {
        User user = userService.userDetails(testUserId);
        Assert.assertEquals(user.getUserId(), testUserId);
    }

    @Test
    public void testGetAllUsers()
    {
        List<User> users = userService.getAllUser();
        Assert.assertTrue(!users.isEmpty());
    }

    @Test
    public void testDeleteUser()
    {
        User user = userService.deleteUser(testUserId);
        Assert.assertFalse(user.isActive());
    }
}

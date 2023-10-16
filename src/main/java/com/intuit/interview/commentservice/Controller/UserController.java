package com.intuit.interview.commentservice.Controller;

import com.intuit.interview.commentservice.Model.Post;
import com.intuit.interview.commentservice.Repositories.PostRepository;
import com.intuit.interview.commentservice.Repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.intuit.interview.commentservice.Model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;
    UserController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public User userDetails(@PathVariable("id") String userId)
    {
        // fetch details from user table and return
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    @GetMapping()
    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String userId)
    {
        // remove user from table with given id
        Optional<User> user = userRepository.findById(userId);
        System.out.println(user);
        user.ifPresent(userRepository::delete);
    }

    @PostMapping("/newUser")
    public void newUser(@RequestBody User user)
    {
        // insert new user in the user table
        userRepository.insert(user);
    }
}

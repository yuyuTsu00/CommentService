package com.intuit.interview.commentservice.User.Controller;

import com.intuit.interview.commentservice.User.Exception.UserNotFoundException;
import com.intuit.interview.commentservice.User.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.intuit.interview.commentservice.User.Model.User;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> userDetails(@PathVariable("id") String userId) throws UserNotFoundException
    {
        // fetch details from user table and return
        return new ResponseEntity<>(userService.userDetails(userId), HttpStatus.FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUser()
    {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") String userId) throws UserNotFoundException
    {
        // remove user from table with given id
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<User> newUser(@RequestBody User user)
    {
        // insert new user in the user table
        return new ResponseEntity<>(userService.newUser(user), HttpStatus.CREATED);
    }
}

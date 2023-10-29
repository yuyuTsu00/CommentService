package com.intuit.interview.commentservice.User.Controller;

import com.intuit.interview.commentservice.User.DTO.UserDto;
import com.intuit.interview.commentservice.User.Exception.UserNotFoundException;
import com.intuit.interview.commentservice.User.Model.User;
import com.intuit.interview.commentservice.User.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get User by userId", description = "userId should be valid")
    @GetMapping("/{id}")
    public ResponseEntity<User> userDetails(@PathVariable("id") String userId)
            throws UserNotFoundException {
        // fetch details from user table and return
        return new ResponseEntity<>(userService.userDetails(userId), HttpStatus.OK);
    }

    @Operation(summary = "get all users")
    @GetMapping()
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @Operation(summary = "delete the user by userId", description = "userId should be valid")
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") String userId)
            throws UserNotFoundException {
        // remove user from table with given id
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }

    @Operation(summary = "Create new user")
    @PostMapping("/new")
    public ResponseEntity<User> newUser(@RequestBody UserDto user) {
        // insert new user in the user table
        return new ResponseEntity<>(userService.newUser(user), HttpStatus.CREATED);
    }
}

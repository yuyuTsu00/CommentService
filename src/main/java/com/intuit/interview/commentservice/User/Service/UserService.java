package com.intuit.interview.commentservice.User.Service;

import com.intuit.interview.commentservice.User.DTO.UserDto;
import com.intuit.interview.commentservice.User.Exception.UserNotFoundException;
import com.intuit.interview.commentservice.User.Model.User;
import java.util.List;

public interface UserService {

    User userDetails(String userId) throws UserNotFoundException;
    List<User> getAllUser();
    User deleteUser(String userId) throws UserNotFoundException;
    User newUser(UserDto user);
}

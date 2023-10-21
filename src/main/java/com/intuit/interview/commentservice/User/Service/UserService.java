package com.intuit.interview.commentservice.User.Service;

import com.intuit.interview.commentservice.User.Exception.UserNotFoundException;
import com.intuit.interview.commentservice.User.Model.User;
import java.util.List;

public interface UserService {

    User userDetails(String userId) throws UserNotFoundException;
    public List<User> getAllUser();
    public User deleteUser(String userId) throws UserNotFoundException;
    public User newUser(User user);
}

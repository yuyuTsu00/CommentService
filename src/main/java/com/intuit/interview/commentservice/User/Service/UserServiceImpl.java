package com.intuit.interview.commentservice.User.Service;

import com.intuit.interview.commentservice.User.DTO.UserDto;
import com.intuit.interview.commentservice.User.Exception.UserNotFoundException;
import com.intuit.interview.commentservice.User.Model.User;
import com.intuit.interview.commentservice.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User userDetails(String userId)
    {
        // fetch details from user table and return
        Optional<User> user = userRepository.findById(userId);
        System.out.println(user);
        if(user.isEmpty())
            throw new UserNotFoundException();

        return user.get();
    }

    @Override
    public List<User> getAllUser()
    {
        return userRepository.getActiveUsers();
    }

    @Override
    public User deleteUser(String userId)
    {
        // remove user from table with given id
        Optional<User> user = userRepository.findById(userId);
        System.out.println(user);

        if(user.isEmpty())
            throw new UserNotFoundException();

        user.get().setActive(false);
        userRepository.save(user.get());
        return user.get();
    }

    @Override
    public User newUser(UserDto user)
    {
        // insert new user in the user table
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        return userRepository.insert(newUser);
    }
}

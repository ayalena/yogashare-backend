package com.eindproject.YogaShare.users;

import com.eindproject.YogaShare.exceptions.UserNotFoundException;
import com.eindproject.YogaShare.userprofiles.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //GET methods
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

//    public Optional<User> getUserById(Long id) {
//        return userRepository.findById(id);
//    }

    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        //check if there is a user by that username
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user;
        } else {
            throw new UserNotFoundException();
        }
    }

    public UserProfile getUserProfile(Long id) {
        //find the user by id
        Optional<User> user = userRepository.findById(id);
        //check if user exists
        if(user.isPresent()) {
            return user.get().getUserProfile();
        } else {
            throw new UserNotFoundException();
        }
    }

    //POST methods
    public String createUser(User userPostRequest) {
        try {
            User user = new User();
            user.setUsername(userPostRequest.getUsername());
            user.setPassword(userPostRequest.getPassword());
            user.setEmail(userPostRequest.getEmail());
            User newUser = userRepository.save(user);
            return newUser.getUsername();
        } catch (Exception ex) {
            throw new UserNotFoundException();
        }
    }


    //PUT methods
//    public void updateUser(String username, User newUser) {
//        Optional<User> userOptional = userRepository.findById(username);
//        //method to update user
//    }


    //DELETE methods
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
    }


}

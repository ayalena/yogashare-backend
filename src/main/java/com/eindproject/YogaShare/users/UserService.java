package com.eindproject.YogaShare.users;

import com.eindproject.YogaShare.exceptions.UserNotFoundException;
import com.eindproject.YogaShare.userprofiles.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {

//    @Autowired
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

//    public User getUserByUsername(String username) {
//        Optional<User> optionalUser = userRepository.findByUsername(username);
//        //check if there is a user by that username
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            return user;
//        } else {
//            throw new UserNotFoundException();
//        }
//    }

    public User getUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user;
        } else {
            throw new UserNotFoundException(username);
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

    //POST methods //wordt signup
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

    //PUT methods wordt afgehandeld in userprofile
    public void updateUsername(Long id, User newUser) {
        //check to see if user exists
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        //update and save it
        User user = userRepository.findById(id).get();
        user.setUsername(newUser.getUsername());
        userRepository.save(user);
    }


    //DELETE methods
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
    }

}

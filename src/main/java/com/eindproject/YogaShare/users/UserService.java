package com.eindproject.YogaShare.users;

import com.eindproject.YogaShare.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    //methods
    public Collection getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(String username) {
        return userRepository.findById(username);
    }

    public String createUser(User userPostRequest) {
        try {
            User user = new User();
            user.setUsername(userPostRequest.getUsername());
            user.setPassword(userPostRequest.getPassword());
            user.setEmail(userPostRequest.getEmail());
            User newUser = userRepository.save(user);
            return newUser.getUsername();
        } catch (Exception ex) {
            throw new RecordNotFoundException();
        }
    }

    public void updateUser(String username, User newUser) {
        Optional<User> userOptional = userRepository.findById(username);
        //method to update user
    }

        public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException();
        }
    }


}

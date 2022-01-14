package com.eindproject.YogaShare.users;

import com.eindproject.YogaShare.authorities.Authority;
import com.eindproject.YogaShare.exceptions.InvalidPasswordException;
import com.eindproject.YogaShare.exceptions.NotAuthorizedException;
import com.eindproject.YogaShare.exceptions.UserNotFoundException;
import com.eindproject.YogaShare.userprofiles.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserCrudRepository userCrudRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserCrudRepository userCrudRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userCrudRepository = userCrudRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //methods regular

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


    //methods authorities

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    public Set<Authority> getAuthorities(String username) {
        Optional<User> userOptional = userCrudRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(username);
        }
        else {
            User user = userOptional.get();
            return user.getAuthorities();
        }
    }

    public void addAuthority(String username, String authorityString) {
        Optional<User> userOptional = userCrudRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(username);
        }
        else {
            User user = userOptional.get();
            user.addAuthority(authorityString);
            userRepository.save(user);
        }
    }

    public void removeAuthority(String username, String authorityString) {
        Optional<User> userOptional = userCrudRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(username);
        }
        else {
            User user = userOptional.get();
            user.removeAuthority(authorityString);
            userRepository.save(user);
        }
    }

    private boolean isValidPassword(String password) {
        final int MIN_LENGTH = 8;
        final int MIN_DIGITS = 1;
        final int MIN_LOWER = 1;
        final int MIN_UPPER = 1;
        final int MIN_SPECIAL = 1;
        final String SPECIAL_CHARS = "@#$%&*!()+=-_";

        long countDigit = password.chars().filter(ch -> ch >= '0' && ch <= '9').count();
        long countLower = password.chars().filter(ch -> ch >= 'a' && ch <= 'z').count();
        long countUpper = password.chars().filter(ch -> ch >= 'A' && ch <= 'Z').count();
        long countSpecial = password.chars().filter(ch -> SPECIAL_CHARS.indexOf(ch) >= 0).count();

        boolean validPassword = true;
        if (password.length() < MIN_LENGTH) validPassword = false;
        if (countLower < MIN_LOWER) validPassword = false;
        if (countUpper < MIN_UPPER) validPassword = false;
        if (countDigit < MIN_DIGITS) validPassword = false;
        if (countSpecial < MIN_SPECIAL) validPassword = false;

        return validPassword;
    }

    public void setPassword(String username, String password) {
        if (username.equals(getCurrentUserName())) {
            if (isValidPassword(password)) {
                Optional<User> userOptional = userCrudRepository.findById(username);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    user.setPassword(passwordEncoder.encode(password));
                    userRepository.save(user);
                }
                else {
                    throw new UserNotFoundException(username);
                }
            }
            else {
                throw new InvalidPasswordException();
            }
        }
        else {
            throw new NotAuthorizedException();
        }
    }

}

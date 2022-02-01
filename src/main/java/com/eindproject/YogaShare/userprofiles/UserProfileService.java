package com.eindproject.YogaShare.userprofiles;

import com.eindproject.YogaShare.exceptions.RecordNotFoundException;
import com.eindproject.YogaShare.exceptions.UserNotFoundException;
import com.eindproject.YogaShare.users.User;
import com.eindproject.YogaShare.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public class UserProfileService {

//    @Autowired
    private UserProfileRepository userProfileRepository;
//    @Autowired
    private UserRepository userRepository;

    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    //methods
    //GET
    public Collection getAllProfiles() {
        return userProfileRepository.findAll();
    }

    //    public Optional getUserProfile(String username) {
//        return userProfileRepository.findByUsername(username);
//    }

    //POST
    public UserProfile saveUserProfile(UserProfile userProfile, Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userRepository.findById(id).get();
            userProfile.setUser(user);
            return userProfileRepository.save(userProfile);
        } else {
            throw new UserNotFoundException();
        }
    }

    //PUT
    public void updateUserProfile(Long id, UserProfile newUserProfile) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(id);
        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileRepository.findById(id).get();
            userProfile.setFirstName(newUserProfile.getFirstName());
            userProfile.setLastName(newUserProfile.getLastName());
            userProfile.setAge(newUserProfile.getAge());
            userProfile.setAddress(newUserProfile.getAddress());
            userProfile.setPostalCode(newUserProfile.getPostalCode());
            userProfile.setCountry(newUserProfile.getCountry());
            userProfileRepository.save(userProfile);
        } else {
            throw new RecordNotFoundException();
        }
    }

}

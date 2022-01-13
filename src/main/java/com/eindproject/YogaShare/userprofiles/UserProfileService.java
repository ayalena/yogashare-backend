package com.eindproject.YogaShare.userprofiles;

import com.eindproject.YogaShare.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public class UserProfileService {

    private UserProfileRepository userProfileRepository;
    private UserRepository userRepository;

    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    //methods
    public Collection getAllProfiles() {
        return userProfileRepository.findAll();
    }

//    public Optional getUserProfile(String username) {
//        return userProfileRepository.findByUsername(username);
//    }
}

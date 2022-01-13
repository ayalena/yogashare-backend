package com.eindproject.YogaShare.userprofiles;

import com.eindproject.YogaShare.users.User;
import com.eindproject.YogaShare.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userprofile")
public class UserProfileController {

    private UserRepository userRepository;
    private UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserRepository userRepository, UserProfileService userProfileService) {
        this.userRepository = userRepository;
        this.userProfileService = userProfileService;
    }

    //methods to save and update user profile
    //but first need authorities



}

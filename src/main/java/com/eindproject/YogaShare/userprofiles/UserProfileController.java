package com.eindproject.YogaShare.userprofiles;

import com.eindproject.YogaShare.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userprofiles")
public class UserProfileController {

    private UserProfileRepository userProfileRepository;
    private UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileRepository userProfileRepository, UserProfileService userProfileService) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileService = userProfileService;
    }




}

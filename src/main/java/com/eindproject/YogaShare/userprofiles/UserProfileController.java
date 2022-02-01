package com.eindproject.YogaShare.userprofiles;

import com.eindproject.YogaShare.users.User;
import com.eindproject.YogaShare.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
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

    //methods

//    @GetMapping("/{id}/userprofile")
////    @PreAuthorize("hasAnyRole('ADMIN','USER')")
//    public ResponseEntity<Object> getUserProfile(@PathVariable("id") Long id) {
//        UserProfile userProfiles = userService.getUserProfile(id);
//        return ResponseEntity.ok(userProfiles);
//    }

    //POST
    @PostMapping("")
    public ResponseEntity<Object> saveUserProfile(@RequestBody UserProfile userProfile, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        UserProfile userProfile1 = userProfileService.saveUserProfile(userProfile, user.getId());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userProfile1).toUri();
        return ResponseEntity.created(location).build();
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserProfile(@PathVariable("id")Long id, @RequestBody UserProfile userProfile) {
        userProfileService.updateUserProfile(id, userProfile);
        return ResponseEntity.ok().build();
    }



}

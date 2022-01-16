package com.eindproject.YogaShare.users;

import com.eindproject.YogaShare.userprofiles.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    //GET
    @GetMapping("") //admin only
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{username}") //for logged-in users and admin
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Object> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @GetMapping("/{id}/userprofile")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Object> getUserProfile(@PathVariable("id") Long id) {
        UserProfile userProfiles = userService.getUserProfile(id);
        return ResponseEntity.ok(userProfiles);
    }

    //POST
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        String newUsername = userService.createUser(user);
        return ResponseEntity.created(URI.create(newUsername)).build();
    }

    //PUT
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> updateUsername(@PathVariable("id") Long id, @RequestBody User user) {
        userService.updateUsername(id, user);
        return ResponseEntity.ok().build();
    }

    //DELETE
    @DeleteMapping("/delete/{id}") //admin only
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}

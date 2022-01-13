package com.eindproject.YogaShare.users;

import com.eindproject.YogaShare.userprofiles.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    @GetMapping("") //only for admin
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

//    @GetMapping("/{id}") //for admin and logged-in users
//    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
//        return ResponseEntity.ok().body(userService.getUserById(id));
//    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @GetMapping("/{id}/userprofile")
    public ResponseEntity<Object> getUserUserProfile(@PathVariable("id") Long id) {
        UserProfile userProfiles = userService.getUserProfile(id);
        return ResponseEntity.ok(userProfiles);
    }

    //POST
    @PostMapping("")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        String newUsername = userService.createUser(user);
        return ResponseEntity.created(URI.create(newUsername)).build();
    }

    //PUT
//    @PutMapping(value = "/{username}")
//    public ResponseEntity<Object> updateUser(@PathVariable("username") String username, @RequestBody User user) {
//        userService.updateUser(username, user);
//        return ResponseEntity.noContent().build();
//    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }




}

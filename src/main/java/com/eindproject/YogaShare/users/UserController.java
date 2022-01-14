package com.eindproject.YogaShare.users;

import com.eindproject.YogaShare.exceptions.BadRequestException;
import com.eindproject.YogaShare.userprofiles.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //methods regular

    //GET
    @GetMapping("") //admin only
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

//    @GetMapping("/{id}") //for admin and logged-in users
//    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
//        return ResponseEntity.ok().body(userService.getUserById(id));
//    }

    @GetMapping("/{username}") //for logged-in users and admin
    public ResponseEntity<Object> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @GetMapping("/{id}/userprofile")
    public ResponseEntity<Object> getUserProfile(@PathVariable("id") Long id) {
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
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateUsername(@PathVariable("id") Long id, @RequestBody User user) {
        userService.updateUsername(id, user);
        return ResponseEntity.ok().build();
    }

    //DELETE
    @DeleteMapping("/delete/{id}") //admin only
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }


    //methods authorities

    //GET
    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    //POST
    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    //PATCH
    @PatchMapping(value = "/{username}/password")
    public ResponseEntity<Object> setPassword(@PathVariable("username") String username, @RequestBody String password) {
        userService.setPassword(username, password);
        return ResponseEntity.noContent().build();
    }

    //DELETE
    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }




}

package com.eindproject.YogaShare.authorities;

import com.eindproject.YogaShare.payload.request.AuthenticationRequest;
import com.eindproject.YogaShare.payload.request.SignupRequest;
import com.eindproject.YogaShare.payload.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/auth")
public class AuthorityController {
    //to login and signup to application

    private AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    //POST
    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest loginRequest) {
        return authorityService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        return authorityService.registerUser(signUpRequest);
    }
}

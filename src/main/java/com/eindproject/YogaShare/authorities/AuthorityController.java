package com.eindproject.YogaShare.authorities;

import com.eindproject.YogaShare.payload.request.AuthenticationRequest;
import com.eindproject.YogaShare.payload.request.SignupRequest;
import com.eindproject.YogaShare.payload.response.AuthenticationResponse;
import com.eindproject.YogaShare.security.service.UserAuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthorityController {
    //to login and signup to application

    private UserAuthenticateService userAuthenticateService;

    @Autowired
    public AuthorityController(UserAuthenticateService userAuthenticateService) {
        this.userAuthenticateService = userAuthenticateService;
    }

    //POST
    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest loginRequest) {
        return userAuthenticateService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        return userAuthenticateService.registerUser(signUpRequest);
    }
}

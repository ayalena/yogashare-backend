package com.eindproject.YogaShare.security.service;

import com.eindproject.YogaShare.authorities.Authority;
import com.eindproject.YogaShare.authorities.AuthorityRepository;
import com.eindproject.YogaShare.authorities.EAuthority;
import com.eindproject.YogaShare.payload.request.AuthenticationRequest;
import com.eindproject.YogaShare.payload.request.SignupRequest;
import com.eindproject.YogaShare.payload.response.AuthenticationResponse;
import com.eindproject.YogaShare.payload.response.MessageResponse;
import com.eindproject.YogaShare.security.jwt.JwtUtil;
import com.eindproject.YogaShare.userdetails.UserDetailsImpl;
import com.eindproject.YogaShare.users.User;
import com.eindproject.YogaShare.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAuthenticateService {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public UserAuthenticateService(AuthenticationManager authenticationManager,
                                   JwtUtil jwtUtil,
                                   AuthorityRepository authorityRepository,
                                   PasswordEncoder passwordEncoder,
                                   UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    //methods to register and sign in
    public ResponseEntity<MessageResponse> registerUser(@Valid SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> authorityRole = signUpRequest.getAuthorities();
        Set<Authority> authorities = new HashSet<>();

        if (authorityRole == null) {
            Authority userRole = (Authority) authorityRepository.findByName(EAuthority.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            authorities.add(userRole);
        } else {
            authorityRole.forEach(role -> {
                switch (role) {
                    case "admin":
                        Authority adminRole = (Authority) authorityRepository.findByName(EAuthority.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        authorities.add(adminRole);

                        break;
                    default:
                        Authority userRole = (Authority) authorityRepository.findByName(EAuthority.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        authorities.add(userRole);
                }
            });
        }

        user.setAuthorities(authorities);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<AuthenticationResponse> authenticateUser(@Valid AuthenticationRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AuthenticationResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
}

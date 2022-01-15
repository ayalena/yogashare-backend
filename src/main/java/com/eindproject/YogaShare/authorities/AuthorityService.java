package com.eindproject.YogaShare.authorities;

import com.eindproject.YogaShare.authorities.EAuthority;
import com.eindproject.YogaShare.payload.request.AuthenticationRequest;
import com.eindproject.YogaShare.payload.request.SignupRequest;
import com.eindproject.YogaShare.payload.response.AuthenticationResponse;
import com.eindproject.YogaShare.payload.response.MessageResponse;
import com.eindproject.YogaShare.security.jwt.JwtUtil;
import com.eindproject.YogaShare.userdetails.UserDetailsImpl;
import com.eindproject.YogaShare.users.User;
import com.eindproject.YogaShare.users.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
public class AuthorityService {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    //methods to register and sign in
    public ResponseEntity<MessageResponse> registerUser(@NotNull @Valid SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> authRole = signupRequest.getAuthority();
        Set<Authority> authorities = new HashSet<>();

        if (authRole == null) {
            Authority role = authorityRepository.findByName(EAuthority.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            authorities.add(role);
        } else {
            authRole.forEach(role -> {
                switch (role) {
                    case "admin":
                        Authority adminRole = authorityRepository.findByName(EAuthority.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        authorities.add(adminRole);

                        break;
                    default:
                        Authority userRole = authorityRepository.findByName(EAuthority.ROLE_USER)
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

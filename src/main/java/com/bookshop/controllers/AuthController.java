package com.bookshop.controllers;

import com.bookshop.dao.User;
import com.bookshop.exceptions.LoginException;
import com.bookshop.models.AuthenticationRequest;
import com.bookshop.models.AuthenticationResponse;
import com.bookshop.repositories.UserRepository;
import com.bookshop.services.MyUserDetailsService;
import com.bookshop.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new LoginException("Incorrect username or password");
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        User user = userRepository.findByUsername(authenticationRequest.getUsername());
        return ResponseEntity.ok(new AuthenticationResponse(jwt, user.getId(), user.getUsername(), user.getRole()));
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signUpDTO){
//        User oldUser = userRepository.findByUsername(signUpDTO.getUsername());
//        if(oldUser != null) {
//            throw new DuplicateRecordException("Username has already exists");
//        }
//        User user = ConvertObject.fromSignUpDTOToUserDAO(signUpDTO);
//        if(user == null) {
//            throw new InvalidException("Invalid user");
//        }
//        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
//        User newUser = userRepository.save(user);
//
//        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(newUser.getUsername());
//        final String jwt = jwtUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new AuthenticationResponse(jwt, newUser.getId(), newUser.getUsername(), newUser.getRole()));
//    }

//    @PostMapping("/validate")
//    public ResponseEntity<?> validateToken(@RequestBody AuthenticationResponse authenticationResponse) {
//        try {
//            String jwt = authenticationResponse.getJwt();
//            String username = jwtUtil.extractUsername(jwt);
//            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
//            if (jwtUtil.validateToken(jwt, userDetails)) {
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//            User user = userRepository.findByUsername(username);
//            return ResponseEntity.ok(new AuthenticationResponse(jwtUtil.generateToken(userDetails), user.getId(),
//                    user.getUsername(), user.getRole()));
//        } catch (Exception e) {
//            throw new InvalidException(e.getMessage());
//        }
//    }
}

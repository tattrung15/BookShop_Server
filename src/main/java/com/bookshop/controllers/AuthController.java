package com.bookshop.controllers;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.bookshop.base.BaseController;
import com.bookshop.dao.User;
import com.bookshop.dto.SignUpDTO;
import com.bookshop.dto.UserResetPasswordDTO;
import com.bookshop.exceptions.AppException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.models.AuthenticationRequest;
import com.bookshop.models.AuthenticationResponse;
import com.bookshop.services.MailService;
import com.bookshop.services.MyUserDetailsService;
import com.bookshop.services.UserService;
import com.bookshop.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController<Object> {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AppException("Incorrect username or password");
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        User user = userService.findByUsername(authenticationRequest.getUsername());
        return this.resSuccess(new AuthenticationResponse(jwt, user));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDTO signUpDTO) {
        User oldUser = userService.findByUsername(signUpDTO.getUsername());
        if (oldUser != null) {
            throw new AppException("Username has already exists");
        }
        User newUser = userService.create(signUpDTO);

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(newUser.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return this.resSuccess(new AuthenticationResponse(jwt, newUser));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody AuthenticationResponse authenticationResponse) {
        try {
            String jwt = authenticationResponse.getJwt();
            String username = jwtUtil.extractUsername(jwt);
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            User user = userService.findByUsername(username);
            return this.resSuccess(new AuthenticationResponse(jwtUtil.generateToken(userDetails), user));
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }

    @DeleteMapping("/password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid UserResetPasswordDTO userResetPasswordDTO, HttpServletRequest request) {
        User user = userService.findByUsername(userResetPasswordDTO.getUsername());

        if (user == null) {
            throw new NotFoundException("Not found user with username");
        }

        String userAgent = request.getHeader("User-Agent");
        String time = new Date().toString();
        String newPassword = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 15);

        Context context = new Context();
        context.setVariable("userAgent", userAgent);
        context.setVariable("time", time);
        context.setVariable("password", newPassword);
        String html = templateEngine.process("password-changed-email.html", context);

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(() -> {
            try {
                mailService.send("Thay đổi mật khẩu", html, user.getEmail(), true);

                user.setPassword(passwordEncoder.encode(newPassword));

                userService.update(user);
            } catch (MessagingException ignored) {
            }
        });

        return this.resSuccess("We have sent a new password to your email address, please check your inbox");
    }

}

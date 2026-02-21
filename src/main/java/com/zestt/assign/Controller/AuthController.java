package com.zestt.assign.Controller;

import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zestt.assign.DTO.Request.LoginRequest;
import com.zestt.assign.DTO.Request.RegisterRequest;
import com.zestt.assign.DTO.Response.AuthResponse;
import com.zestt.assign.Entity.RefreshToken;
import com.zestt.assign.Entity.Role;
import com.zestt.assign.Entity.User;
import com.zestt.assign.Exception.UserException;
import com.zestt.assign.Repository.RefreshTokenRepository;
import com.zestt.assign.Repository.UserRepository;
import com.zestt.assign.Security.UserDetails.CustomUserdetails;
import com.zestt.assign.Security.jwt.JwtUtil;
import com.zestt.assign.Service.Implementation.RefreshTokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

   
    
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    

    @PostMapping("/login")
public ResponseEntity<AuthResponse> login(
        @RequestBody LoginRequest request) {

           

    Authentication authentication =
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(),
                            request.getPassword()
                    )
            );

    CustomUserdetails userDetails =
            (CustomUserdetails) authentication.getPrincipal();
System.out.println("Authcontrooler userdetails"+userDetails.getUsername());
     //Fetch full user entity
        User user = userRepository.findByUserName(
                userDetails.getUsername()
        ).orElseThrow(() ->
                new UserException("User not found"));
        
                System.out.println("user of authcontroller"+user.getUserName());

    String accessToken = jwtUtil.generateToken(userDetails);

    System.out.println("accessToken"+accessToken);

    RefreshToken refreshToken =
            refreshTokenService.createRefreshToken(user);
 
            System.out.println("refreshtoken"+refreshToken.getToken());


    return ResponseEntity.ok(
            new AuthResponse(
                    accessToken,
                    refreshToken.getToken()
            )
    );
}
//Sign Up
@PostMapping("/register")
public ResponseEntity<String> register(
        @RequestBody RegisterRequest request) {

    if (userRepository.existsByUserName(request.getUserName())) {
        throw new UserException("Username already taken");
    }

    User user = new User();
    user.setUserName(request.getUserName());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    
   Role role = request.getRole(); // This is already Role enum

if (role == null) {
    role = Role.ROLE_USER; // default role
}

    user.setRole(role);

    userRepository.save(user);

    return ResponseEntity.ok("User registered successfully");
}

@PostMapping("/refresh")
public ResponseEntity<AuthResponse> refreshToken(
        @RequestBody Map<String, String> request) {

    String requestToken = request.get("refreshToken");

    RefreshToken refreshToken = refreshTokenRepository
            .findByToken(requestToken)
            .orElseThrow(() ->
                    new UserException("Refresh token not found"));

    refreshTokenService.verifyExpiration(refreshToken);

    User user = refreshToken.getUser();

    UserDetails userDetails =
            new CustomUserdetails(
                   user
            );


    String newAccessToken =
            jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(
            new AuthResponse(
                    newAccessToken,
                    requestToken
            )
    );
}

}

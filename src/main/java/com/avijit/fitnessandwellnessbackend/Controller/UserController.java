package com.avijit.fitnessandwellnessbackend.Controller;

import com.avijit.fitnessandwellnessbackend.Config.JwtTokenUtil;
import com.avijit.fitnessandwellnessbackend.DTO.LoginRequestDto;
import com.avijit.fitnessandwellnessbackend.DTO.RegisterRequestDto;
import com.avijit.fitnessandwellnessbackend.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public UserController(UserDetailsService userDetailsService, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
// user registration
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto registerRequestDto){
        if (registerRequestDto == null){
            return ResponseEntity.badRequest().body("Invalid Request");
        }
        if (registerRequestDto.getName() == null
                || registerRequestDto.getEmail() == null
                || registerRequestDto.getPassword() == null
                || registerRequestDto.getAge() <= 0
                || registerRequestDto.getHeight() <= 0
                || registerRequestDto.getWeight() <= 0
                || registerRequestDto.getGender()==0){
            return ResponseEntity.badRequest().body("Invalid Request");
        }
        userService.registerUser(registerRequestDto);

           return ResponseEntity.created( null).body("User Registered");
    }

//    user login
    @PostMapping("/login")
    public  ResponseEntity<?> loginUser(@CookieValue(name = "") @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        if (loginRequestDto == null){
            return ResponseEntity.badRequest().body("Invalid Request");
        }
        if (loginRequestDto.getEmail() == null || loginRequestDto.getPassword() == null){
            return ResponseEntity.badRequest().body("Invalid Request");
        }
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getEmail());
            userService.loginUser(loginRequestDto);
            String jwtToken = jwtTokenUtil.generateToken(userDetails);
            String encodedJwtToken = URLEncoder.encode("Bearer "+jwtToken, StandardCharsets.UTF_8);
            Cookie cookie = new Cookie("authorization", encodedJwtToken);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 60 * 10);
            cookie.setSecure(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok("Login Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid Request");
        }


    }

//    Profile
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request){

        return ResponseEntity.ok("Profile");
    }


}

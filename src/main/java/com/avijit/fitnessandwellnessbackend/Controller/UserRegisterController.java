package com.avijit.fitnessandwellnessbackend.Controller;

import com.avijit.fitnessandwellnessbackend.Config.JwtTokenUtil;
import com.avijit.fitnessandwellnessbackend.DTO.LoginRequestDto;
import com.avijit.fitnessandwellnessbackend.DTO.RegisterRequestDto;
import com.avijit.fitnessandwellnessbackend.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserRegisterController {
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public UserRegisterController(UserDetailsService userDetailsService, UserService userService,JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
// user registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDto registerRequestDto){
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
    public  ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        if (loginRequestDto == null){
            return ResponseEntity.badRequest().body("Invalid Request");
        }
        if (loginRequestDto.getEmail() == null || loginRequestDto.getPassword() == null){
            return ResponseEntity.badRequest().body("Invalid Request");
        }
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getEmail());
            Cookie cookie = new Cookie("authorize", jwtTokenUtil.generateToken(userDetails));
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 60 * 10);
            cookie.setSecure(true);
            response.addCookie(cookie);
            return ResponseEntity.ok("Login Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid Request");
        }


    }





}

package com.avijit.fitnessandwellnessbackend.Controller;

import com.avijit.fitnessandwellnessbackend.Config.JwtTokenUtil;
import com.avijit.fitnessandwellnessbackend.DTO.UserRequestDto;
import com.avijit.fitnessandwellnessbackend.Service.UserService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserRegisterController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public UserRegisterController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDto userRequestDto){
        if (userRequestDto == null){
            return ResponseEntity.badRequest().body("Invalid Request");
        }
        if (userRequestDto.getName() == null || userRequestDto.getEmail() == null || userRequestDto.getPassword() == null || userRequestDto.getAge() == 0 || userRequestDto.getHeight() == 0 || userRequestDto.getWeight() == 0 || userRequestDto.getGender()==0){
            return ResponseEntity.badRequest().body("Invalid Request");
        }
        userService.registerUser(userRequestDto);

           return ResponseEntity.created( null).body("User Registered");
    }


}

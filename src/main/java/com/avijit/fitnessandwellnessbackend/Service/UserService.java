package com.avijit.fitnessandwellnessbackend.Service;

import com.avijit.fitnessandwellnessbackend.DTO.LoginRequestDto;
import com.avijit.fitnessandwellnessbackend.DTO.RegisterRequestDto;
import com.avijit.fitnessandwellnessbackend.Exception.NotFound;
import com.avijit.fitnessandwellnessbackend.Model.UserModel;
import com.avijit.fitnessandwellnessbackend.Reposetory.UserRepo;
import jakarta.servlet.http.Cookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserInterface {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // user registration
    @Override
    public void registerUser(RegisterRequestDto registerRequestDto) {
        UserModel userModel = new UserModel();
        userModel.setName(registerRequestDto.getName());
        userModel.setEmail(registerRequestDto.getEmail());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userModel.setPassword(bCryptPasswordEncoder.encode(registerRequestDto.getPassword()));
        userModel.setAge(registerRequestDto.getAge());
        userModel.setHeight(registerRequestDto.getHeight());
        userModel.setWeight(registerRequestDto.getWeight());
        userModel.setGender(registerRequestDto.getGender());
        userRepo.save(userModel);
    }

    // user login
    @Override
    public void loginUser(LoginRequestDto loginRequestDto) throws NotFound {
        UserModel userModel = userRepo.findByEmail(loginRequestDto.getEmail());
        if (userModel == null) {
            throw new NotFound("User Not Found");
        }
        if (loginRequestDto.getPassword() == null) {
            throw new RuntimeException("Invalid Password");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), userModel.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

    }



}

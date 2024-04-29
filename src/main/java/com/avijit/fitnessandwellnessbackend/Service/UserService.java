package com.avijit.fitnessandwellnessbackend.Service;

import com.avijit.fitnessandwellnessbackend.DTO.*;
import com.avijit.fitnessandwellnessbackend.Exception.NotFound;
import com.avijit.fitnessandwellnessbackend.Model.UserModel;
import com.avijit.fitnessandwellnessbackend.Model.UserRole;
import com.avijit.fitnessandwellnessbackend.Reposetory.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserInterface {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // user registration
    @Override
    public void registerUser(RegisterRequestDto registerRequestDto) {
        UserModel userFind=userRepo.findByEmail(registerRequestDto.getEmail());
        if (userFind!=null){
            throw new RuntimeException("User Already Exist");
        }


        UserModel userModel = new UserModel();
        userModel.setName(registerRequestDto.getName());
        userModel.setEmail(registerRequestDto.getEmail());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userModel.setPassword(bCryptPasswordEncoder.encode(registerRequestDto.getPassword()));
        userModel.setAge(registerRequestDto.getAge());
        userModel.setHeight(registerRequestDto.getHeight());
        userModel.setWeight(registerRequestDto.getWeight());
        userModel.setGender(registerRequestDto.getGender());
        userModel.setRole(UserRole.USER);
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

    @Override
    public ProfileResponseDto getProfile(ProfileRequestDto profileRequestDto) throws NotFound {
        Optional<UserModel> userModel = Optional.ofNullable(userRepo.findByEmail(profileRequestDto.getEmail()));
        if (userModel.isPresent()) {
            ProfileResponseDto profileResponseDto = new ProfileResponseDto();
            profileResponseDto.setName(userModel.get().getName());
            profileResponseDto.setEmail(userModel.get().getEmail());
            profileResponseDto.setAge(userModel.get().getAge());
            profileResponseDto.setHeight(userModel.get().getHeight());
            profileResponseDto.setWeight(userModel.get().getWeight());
            profileResponseDto.setGender(userModel.get().getGender());
            return profileResponseDto;
        }
        return null;
    }

    @Override
    public void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto, ProfileRequestDto profileRequestDto) throws NotFound {
        Optional<UserModel> userModel = Optional.ofNullable(userRepo.findByEmail(profileRequestDto.getEmail()));

        if (userModel.isPresent()) {
            userModel.get().setName(profileUpdateRequestDto.getName());
            userModel.get().setAge(profileUpdateRequestDto.getAge());
            userModel.get().setHeight(profileUpdateRequestDto.getHeight());
            userModel.get().setWeight(profileUpdateRequestDto.getWeight());
            userModel.get().setGender(profileUpdateRequestDto.getGender());
            userRepo.save(userModel.get());
        }
    }

    @Override
    public void logoutUser() {

    }


}

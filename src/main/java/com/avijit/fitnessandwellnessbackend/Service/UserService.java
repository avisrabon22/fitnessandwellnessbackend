package com.avijit.fitnessandwellnessbackend.Service;

import com.avijit.fitnessandwellnessbackend.DTO.UserRequestDto;
import com.avijit.fitnessandwellnessbackend.Model.UserModel;
import com.avijit.fitnessandwellnessbackend.Reposetory.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserInterface {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void registerUser(UserRequestDto userRequestDto){
        UserModel userModel = new UserModel();
        userModel.setName(userRequestDto.getName());
        userModel.setEmail(userRequestDto.getEmail());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userModel.setPassword(bCryptPasswordEncoder.encode(userRequestDto.getPassword()));
        userModel.setAge(userRequestDto.getAge());
        userModel.setHeight(userRequestDto.getHeight());
        userModel.setWeight(userRequestDto.getWeight());
        userModel.setGender(userRequestDto.getGender());
        userRepo.save(userModel);
    }

}

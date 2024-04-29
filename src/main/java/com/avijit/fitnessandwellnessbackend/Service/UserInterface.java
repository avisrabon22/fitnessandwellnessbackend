package com.avijit.fitnessandwellnessbackend.Service;

import com.avijit.fitnessandwellnessbackend.DTO.LoginRequestDto;
import com.avijit.fitnessandwellnessbackend.DTO.RegisterRequestDto;
import com.avijit.fitnessandwellnessbackend.Exception.NotFound;

public interface UserInterface {
    void registerUser(RegisterRequestDto registerRequestDto);
    void loginUser(LoginRequestDto loginRequestDto) throws NotFound;
    void logoutUser();
}

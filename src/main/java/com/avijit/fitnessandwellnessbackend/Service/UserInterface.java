package com.avijit.fitnessandwellnessbackend.Service;

import com.avijit.fitnessandwellnessbackend.DTO.*;
import com.avijit.fitnessandwellnessbackend.Exception.NotFound;

public interface UserInterface {
    void registerUser(RegisterRequestDto registerRequestDto);
    void loginUser(LoginRequestDto loginRequestDto) throws NotFound;
    ProfileResponseDto getProfile(ProfileRequestDto profileRequestDto) throws NotFound;
    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto,ProfileRequestDto profileRequestDto) throws NotFound;
    void logoutUser();
}

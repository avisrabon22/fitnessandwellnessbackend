package com.avijit.fitnessandwellnessbackend.DTO;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileResponseDto {
    private String name;
    private String email;
    private int age;
    private double height;
    private double weight;
    private char gender;
}

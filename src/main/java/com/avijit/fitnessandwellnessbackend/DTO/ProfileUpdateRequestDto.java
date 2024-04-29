package com.avijit.fitnessandwellnessbackend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateRequestDto {
    private String name;
    private int age;
    private double height;
    private double weight;
    private char gender;
}

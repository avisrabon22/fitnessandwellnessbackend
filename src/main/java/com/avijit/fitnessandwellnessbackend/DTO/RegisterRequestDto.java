package com.avijit.fitnessandwellnessbackend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {
    private String name;
    private String email;
    private String password ;
    private int age;
    private double height;
    private double weight;
    private char gender;
}

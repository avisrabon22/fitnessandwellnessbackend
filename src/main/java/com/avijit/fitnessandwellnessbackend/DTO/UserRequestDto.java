package com.avijit.fitnessandwellnessbackend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String name;
    private String email;
    private String password ;
    private int age;
    private int height;
    private int weight;
    private char gender;
}

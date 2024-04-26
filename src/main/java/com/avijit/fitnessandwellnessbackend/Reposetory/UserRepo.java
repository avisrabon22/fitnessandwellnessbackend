package com.avijit.fitnessandwellnessbackend.Reposetory;


import com.avijit.fitnessandwellnessbackend.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Long> {
}

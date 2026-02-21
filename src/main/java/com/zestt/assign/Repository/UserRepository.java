package com.zestt.assign.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zestt.assign.Entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

     Optional<User> findByUserName(String userName);

     boolean existsByUserName(String username);

}

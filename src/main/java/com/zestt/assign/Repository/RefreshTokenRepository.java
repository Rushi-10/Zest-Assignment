package com.zestt.assign.Repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zestt.assign.Entity.RefreshToken;
import com.zestt.assign.Entity.User;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);

     void deleteByUser(User user);
    
}   

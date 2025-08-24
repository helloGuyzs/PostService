package com.example.cloudsek.repo;

import com.example.cloudsek.model.userModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@Repository
public interface autheRepo extends JpaRepository<userModel, Integer> {

    Optional<userModel> findByUsername(String username);
    Optional<userModel> findByEmail(String email);
}

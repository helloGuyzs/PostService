package com.example.cloudsek.repo;

import com.example.cloudsek.model.postModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface postRepo extends JpaRepository<postModel, Integer> {

//    Optional<postModel> findById(String title, Integer userId);

    List<postModel> findByUserId(Integer userId);

    Optional<postModel> findByIdAndUserId(Integer id , Integer userId);

    List<postModel> findByIsActiveTrue();
    List<postModel> findByUserIdAndIsActiveTrue(Integer userId);
    Optional<postModel> findByIdAndIsActiveTrue(Integer id);
    Optional<postModel> findByIdAndUserIdAndIsActiveTrue(Integer id, Integer userId);

}

package com.example.cloudsek.repo;


import com.example.cloudsek.model.commentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface commentRepo extends JpaRepository<commentModel, Integer> {

    Optional<commentModel> findByIdAndUserId(Integer id , Integer userId);
    List<commentModel> findByPostId(Integer postId);
}

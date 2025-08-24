package com.example.cloudsek.service;

import com.example.cloudsek.dto.addpostDto;
import com.example.cloudsek.dto.commentResponse;
import com.example.cloudsek.dto.postResponseDto;
import com.example.cloudsek.dto.responseHandlerDto;
import com.example.cloudsek.exceptions.NotFoundException;
import com.example.cloudsek.model.commentModel;
import com.example.cloudsek.model.postModel;
import com.example.cloudsek.repo.commentRepo;
import com.example.cloudsek.repo.postRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class postService {

    @Autowired
    private postRepo postRepo;

    @Autowired
    private commentRepo commentRepo;

    private List<commentResponse> convertCommentDto(List<commentModel> comments) {
        List<commentResponse> commentDtos = new ArrayList<>();
        for (commentModel comment : comments) {
            commentResponse dto = new commentResponse();
            dto.setComment(comment.getComment());
            dto.setUserId(comment.getUserId());
            commentDtos.add(dto);
        }
        return commentDtos;
    }

    public List<postResponseDto> getAllPosts() {
        List<postModel> posts = postRepo.findByIsActiveTrue();
        List<postResponseDto> res = new ArrayList<>();

        for (postModel post : posts) {
            postResponseDto postRes = new postResponseDto();
            postRes.setPostId(post.getId());
            postRes.setContent(post.getContent());
            postRes.setUserId(post.getUserId());

            List<commentModel> comments = commentRepo.findByPostId(post.getId());
            postRes.setComments(convertCommentDto(comments));

            res.add(postRes);
        }
        return res;
    }

    public postResponseDto getPostById(Integer postId, Integer userId) {
        Optional<postModel> optionalPost = postRepo.findByIdAndUserIdAndIsActiveTrue(postId, userId);

        if (optionalPost.isEmpty()) {
            throw new NotFoundException("Post with ID " + postId + " not found for user " + userId);
        }

        postModel post = optionalPost.get();
        List<commentModel> comments = commentRepo.findByPostId(postId);

        postResponseDto postRes = new postResponseDto();
        postRes.setPostId(post.getId());
        postRes.setContent(post.getContent());
        postRes.setUserId(post.getUserId());
        postRes.setComments(convertCommentDto(comments));

        return postRes;
    }

    public List<postResponseDto> getPostByUserId(Integer userId) {
        List<postModel> posts = postRepo.findByUserIdAndIsActiveTrue(userId);
        List<postResponseDto> res = new ArrayList<>();

        for (postModel post : posts) {
            postResponseDto dto = new postResponseDto();
            dto.setPostId(post.getId());
            dto.setContent(post.getContent());
            dto.setUserId(userId);

            List<commentModel> comments = commentRepo.findByPostId(post.getId());
            dto.setComments(convertCommentDto(comments));

            res.add(dto);
        }
        return res;
    }

    public postModel addPost(addpostDto req, Integer userId) {
        postModel post = new postModel();
        post.setContent(req.getContent());
        post.setUserId(userId);
        postRepo.save(post);
        return post;
    }

    public postModel updatePost(Integer postId, addpostDto req, Integer userId) {
        Optional<postModel> optionalPost = postRepo.findByIdAndUserIdAndIsActiveTrue(postId, userId);

        if (optionalPost.isEmpty()) {
            throw new NotFoundException("Post with ID " + postId + " not found for user " + userId);
        }

        postModel post = optionalPost.get();
        post.setContent(req.getContent());
        postRepo.save(post);

        return post;
    }

    public responseHandlerDto softDeletePost(Integer postId, Integer userId) {
        Optional<postModel> optionalPost = postRepo.findByIdAndUserIdAndIsActiveTrue(postId, userId);

        if (optionalPost.isEmpty()) {
            throw new NotFoundException("Post with ID " + postId + " not found for user " + userId);
        }

        postModel post = optionalPost.get();
        post.setActive(false);
        postRepo.save(post);

        responseHandlerDto res = new responseHandlerDto();
        res.setMessage("Post soft deleted successfully!");
        return res;
    }

    public responseHandlerDto deletePost(Integer postId, Integer userId) {
        Optional<postModel> optionalPost = postRepo.findByIdAndUserId(postId, userId);

        if (optionalPost.isEmpty()) {
            throw new NotFoundException("Post with ID " + postId + " not found for user " + userId);
        }

        postModel post = optionalPost.get();
        postRepo.delete(post);

        responseHandlerDto res = new responseHandlerDto();
        res.setMessage("Post deleted successfully!");
        return res;
    }
}

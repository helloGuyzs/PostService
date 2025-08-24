package com.example.cloudsek.controller;

import com.example.cloudsek.dto.addpostDto;
import com.example.cloudsek.dto.commentResponse;
import com.example.cloudsek.dto.postResponseDto;
import com.example.cloudsek.dto.responseHandlerDto;
import com.example.cloudsek.model.postModel;
import com.example.cloudsek.service.authService;
import com.example.cloudsek.service.postService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class postControllerTest {

    @InjectMocks
    private postController postController;

    @Mock
    private postService postService;

    @Mock
    private authService authService;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHealthCheck() {
        String response = postController.healthCheck();
        assertEquals("Hello from the private endpoint!", response);
    }

    @Test
    void testAddPost() {
        addpostDto dto = new addpostDto();
        dto.setContent("Test Content");

        postModel post = new postModel();
        post.setId(1);
        post.setContent("Test Content");
        post.setUserId(100);

        when(authService.getCurrentUserId(request)).thenReturn(100);
        when(postService.addPost(dto, 100)).thenReturn(post);

        postModel result = postController.addPost(dto, request);

        assertEquals(post.getId(), result.getId());
        assertEquals(post.getContent(), result.getContent());
        assertEquals(post.getUserId(), result.getUserId());
    }

    @Test
    void testUpdatePost() {
        addpostDto dto = new addpostDto();
        dto.setContent("Updated Content");

        postModel post = new postModel();
        post.setId(1);
        post.setContent("Updated Content");
        post.setUserId(100);

        when(authService.getCurrentUserId(request)).thenReturn(100);
        when(postService.updatePost(1, dto, 100)).thenReturn(post);

        postModel result = postController.updatePost(1, dto, request);

        assertEquals(post.getContent(), result.getContent());
        assertEquals(post.getUserId(), result.getUserId());
    }

    @Test
    void testGetAllPosts() {
        List<postResponseDto> posts = new ArrayList<>();
        postResponseDto dto = new postResponseDto();
        dto.setPostId(1);
        dto.setContent("Post 1");
        dto.setUserId(100);
        dto.setComments(new ArrayList<commentResponse>());
        posts.add(dto);

        when(postService.getAllPosts()).thenReturn(posts);

        List<postResponseDto> result = postController.getAllPosts();

        assertEquals(1, result.size());
        assertEquals("Post 1", result.get(0).getContent());
    }

    @Test
    void testGetPostsByUserId() {
        List<postResponseDto> posts = new ArrayList<>();
        postResponseDto dto = new postResponseDto();
        dto.setPostId(1);
        dto.setContent("User Post");
        dto.setUserId(100);
        dto.setComments(new ArrayList<commentResponse>());
        posts.add(dto);

        when(authService.getCurrentUserId(request)).thenReturn(100);
        when(postService.getPostByUserId(100)).thenReturn(posts);

        List<postResponseDto> result = postController.getPostsByUserId(request);

        assertEquals(1, result.size());
        assertEquals("User Post", result.get(0).getContent());
    }

    @Test
    void testGetPost() {
        postResponseDto dto = new postResponseDto();
        dto.setPostId(1);
        dto.setContent("Single Post");
        dto.setUserId(100);
        dto.setComments(new ArrayList<commentResponse>());

        when(authService.getCurrentUserId(request)).thenReturn(100);
        when(postService.getPostById(1, 100)).thenReturn(dto);

        postResponseDto result = postController.getPost(1, request);

        assertEquals(dto.getPostId(), result.getPostId());
        assertEquals(dto.getContent(), result.getContent());
    }

    @Test
    void testSoftDeletePost() {
        responseHandlerDto res = new responseHandlerDto();
        res.setMessage("Post soft deleted successfully!");

        when(authService.getCurrentUserId(request)).thenReturn(100);
        when(postService.softDeletePost(1, 100)).thenReturn(res);

        responseHandlerDto result = postController.softDeletePost(1, request);

        assertEquals("Post soft deleted successfully!", result.getMessage());
    }

    @Test
    void testDeletePost() {
        responseHandlerDto res = new responseHandlerDto();
        res.setMessage("Post deleted successfully!");

        when(authService.getCurrentUserId(request)).thenReturn(100);
        when(postService.deletePost(1, 100)).thenReturn(res);

        responseHandlerDto result = postController.deletePost(1, request);

        assertEquals("Post deleted successfully!", result.getMessage());
    }
}

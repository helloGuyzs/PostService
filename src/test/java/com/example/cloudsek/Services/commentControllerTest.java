package com.example.cloudsek.controller;

import com.example.cloudsek.dto.addCommentDto;
import com.example.cloudsek.dto.responseHandlerDto;
import com.example.cloudsek.model.commentModel;
import com.example.cloudsek.service.authService;
import com.example.cloudsek.service.commentService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class commentControllerTest {

    @InjectMocks
    private commentController commentController;

    @Mock
    private commentService commentService;

    @Mock
    private authService authService;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComment() {
        addCommentDto dto = new addCommentDto();
        dto.setPostId(1);
        dto.setComment("Test Comment");

        commentModel comment = new commentModel();
        comment.setId(1);
        comment.setPostId(1);
        comment.setUserId(100);
        comment.setComment("Test Comment");

        when(authService.getCurrentUserId(request)).thenReturn(100);
        when(commentService.addComment(dto, 100)).thenReturn(comment);

        commentModel result = commentController.addComment(dto, request);

        assertEquals(comment.getId(), result.getId());
        assertEquals(comment.getComment(), result.getComment());
        assertEquals(comment.getUserId(), result.getUserId());
    }

    @Test
    void testUpdateComment() {
        addCommentDto dto = new addCommentDto();
        dto.setComment("Updated Comment");
        dto.setPostId(1);

        commentModel comment = new commentModel();
        comment.setId(1);
        comment.setPostId(1);
        comment.setUserId(100);
        comment.setComment("Updated Comment");

        when(authService.getCurrentUserId(request)).thenReturn(100);
        when(commentService.updateComment(1, dto, 100)).thenReturn(comment);

        commentModel result = commentController.updateComment(1, dto, request);

        assertEquals("Updated Comment", result.getComment());
        assertEquals(100, result.getUserId());
    }

    @Test
    void testGetComment() {
        commentModel comment = new commentModel();
        comment.setId(1);
        comment.setPostId(1);
        comment.setUserId(100);
        comment.setComment("Sample Comment");

        when(authService.getCurrentUserId(request)).thenReturn(100);
        when(commentService.getComment(1, 100)).thenReturn(comment);

        commentModel result = commentController.getComment(1, request);

        assertEquals(1, result.getId());
        assertEquals("Sample Comment", result.getComment());
        assertEquals(100, result.getUserId());
    }

    @Test
    void testDeleteComment() {
        responseHandlerDto res = new responseHandlerDto();
        res.setMessage("Comment deleted successfully!");

        when(authService.getCurrentUserId(request)).thenReturn(100);
        when(commentService.deleteComment(1, 100)).thenReturn(res);

        responseHandlerDto result = commentController.deleteComment(1, request);

        assertEquals("Comment deleted successfully!", result.getMessage());
    }
}

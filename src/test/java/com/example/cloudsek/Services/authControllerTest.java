package com.example.cloudsek.Services;

import com.example.cloudsek.dto.addUserDto;
import com.example.cloudsek.dto.responseHandlerDto;
import com.example.cloudsek.dto.userLoginDto;
import com.example.cloudsek.model.userModel;
import com.example.cloudsek.repo.autheRepo;
import com.example.cloudsek.service.authService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class authControllerTest {@InjectMocks
private authService authService;

    @Mock
    private autheRepo authrepo;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext(); // reset before each test
    }

    @Test
    void testRegister_NewUser_Success() {
        addUserDto dto = new addUserDto("n@x.com", "Nikhil", "nikhil", "pass", "1234");
        userModel savedUser = new userModel();
        savedUser.setId(1);
        savedUser.setUsername("nikhil");

        when(authrepo.save(any(userModel.class))).thenReturn(savedUser);

        userModel result = authService.register(dto);

        assertEquals("nikhil", result.getUsername());
        verify(authrepo, times(1)).save(any(userModel.class));
    }

    @Test
    void testLogin_Success() {
        userLoginDto dto = new userLoginDto("nikhil", "pass");
        userModel user = new userModel();
        user.setUsername("nikhil");
        user.setPassword("pass");

        when(authrepo.findByUsername("nikhil")).thenReturn(Optional.of(user));
        when(request.getSession(true)).thenReturn(session);

        responseHandlerDto result = authService.login(dto, request);

        assertTrue(result.getMessage().contains("User logged in successfully"));
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testLogin_InvalidPassword() {
        userLoginDto dto = new userLoginDto("nikhil", "wrong");
        userModel user = new userModel();
        user.setUsername("nikhil");
        user.setPassword("pass");

        when(authrepo.findByUsername("nikhil")).thenReturn(Optional.of(user));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            authService.login(dto, request);
        });

        assertTrue(ex.getMessage().contains("Invalid password"));
    }

    @Test
    void testLogout_Success() {
        when(request.getSession(false)).thenReturn(session);

        responseHandlerDto result = authService.logout(request);

        assertTrue(result.getMessage().contains("User logged out successfully"));
        verify(session, times(1)).invalidate();
    }
}

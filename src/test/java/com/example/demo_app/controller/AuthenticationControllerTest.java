package com.example.demo_app.controller;

import com.example.demo_app.exception.ConflictException;
import com.example.demo_app.exception.UnauthorizedException;
import com.example.demo_app.model.AuthenticationRequest;
import com.example.demo_app.model.AuthenticationResponse;
import com.example.demo_app.model.RegisterRequest;
import com.example.demo_app.model.Role;
import com.example.demo_app.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService service;

    @InjectMocks
    private AuthenticationController controller;

    @Test
    public void testRegister_success() {
        // Mock service behavior
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .role(Role.USER)
                .build();
        AuthenticationResponse expectedResponse = new AuthenticationResponse("access_token_value", "refresh_token_value");
        Mockito.when(service.register(registerRequest)).thenReturn(expectedResponse);

        // Call the controller method
        ResponseEntity<AuthenticationResponse> response = controller.register(registerRequest);

        // Assert response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test(expected = BadRequestException.class)
    public void testRegister_missingEmail() {
        RegisterRequest request = new RegisterRequest("", "Doe", null, "password", Role.USER);
        controller.register(request);
    }

    @Test(expected = BadRequestException.class)
    public void testRegister_invalidEmailFormat() {
        RegisterRequest request = new RegisterRequest("John", "Doe", "invalid_email", "password", Role.USER);
        controller.register(request);
    }

    @Test(expected = ConflictException.class)
    public void testRegister_userAlreadyExists() {
        Mockito.when(service.register(Mockito.any(RegisterRequest.class))).thenThrow(new ConflictException("User already exists"));
        RegisterRequest request = new RegisterRequest("John", "Doe", "john.doe@example.com", "password", Role.USER);
        controller.register(request);
    }

    @Test(expected = UnauthorizedException.class)
    public void testAuthenticate_invalidCredentials() {
        Mockito.when(service.authenticate(Mockito.any(AuthenticationRequest.class))).thenThrow(new UnauthorizedException("Invalid credentials"));
        AuthenticationRequest request = new AuthenticationRequest("invalid_user", "invalid_password");
        controller.authenticate(request);
    }

    @Test
    public void testRefreshToken_success() throws IOException {
        // Mock service behavior
        Mockito.doNothing().when(service).refreshToken(Mockito.any(HttpServletRequest.class), Mockito.any(HttpServletResponse.class));

        controller.refreshToken(Mockito.mock(HttpServletRequest.class), Mockito.mock(HttpServletResponse.class));

        // Verify service call
        Mockito.verify(service).refreshToken(Mockito.any(HttpServletRequest.class), Mockito.any(HttpServletResponse.class));
    }


}


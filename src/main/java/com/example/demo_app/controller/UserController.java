package com.example.demo_app.controller;

import com.example.demo_app.dto.UserDto;
import com.example.demo_app.mapper.UserMapper;
import com.example.demo_app.repository.UserRepository;
import com.example.demo_app.service.UserService;
import lombok.RequiredArgsConstructor;
import com.example.demo_app.model.ChangePasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserRepository repository;

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> list() {
        return ResponseEntity.ok(UserMapper.INSTANCE
                .usersToUserDtos(repository.findAll()));
    }
}

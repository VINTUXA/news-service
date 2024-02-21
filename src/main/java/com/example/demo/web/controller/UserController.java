package com.example.demo.web.controller;

import com.example.demo.mapper.V1.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.web.model.UpsetUserRequest;
import com.example.demo.web.model.UserListResponse;
import com.example.demo.web.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userServiceImpl;
    private final UserMapper userMapper;


    @GetMapping
    public ResponseEntity<UserListResponse> findAll(){
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(userServiceImpl.findAll())
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                userMapper.userToResponse(userServiceImpl.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UpsetUserRequest upsetUserRequest){
        User newUser = userServiceImpl.save(userMapper.requestToUser(upsetUserRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                                 @RequestBody UpsetUserRequest request){
        User updatedUser  = userServiceImpl.update(userMapper.requestToUser(id, request));
        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userServiceImpl.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

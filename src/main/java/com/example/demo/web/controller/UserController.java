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
    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping
    public ResponseEntity<UserListResponse> findAll(){
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(userService.findAll())
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                userMapper.userToResponse(userService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UpsetUserRequest upsetUserRequest){
        User newUser = userService.save(userMapper.requestToUser(upsetUserRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                                 @RequestBody UpsetUserRequest request){
        User updatedUser  = userService.update(userMapper.requestToUser(id, request));
        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

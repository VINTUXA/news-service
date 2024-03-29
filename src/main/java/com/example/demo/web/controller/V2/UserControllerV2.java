package com.example.demo.web.controller.V2;

import com.example.demo.mapper.V2.UserMapperV2;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.web.model.UpsetUserRequest;
import com.example.demo.web.model.UserFilter;
import com.example.demo.web.model.UserListResponse;
import com.example.demo.web.model.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/user")
@RequiredArgsConstructor
public class UserControllerV2 {

    private final UserService databaseUserService;
    private final UserMapperV2 userMapper;

    @GetMapping("/filter")
    public ResponseEntity<UserListResponse> filterBy(@Valid UserFilter filter) {
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(databaseUserService.filterBy(filter))
        );
    }

    @GetMapping
    public ResponseEntity<UserListResponse> findAll(UserFilter filter) {
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(databaseUserService.findAll(filter))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(
                userMapper.userToResponse(databaseUserService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UpsetUserRequest upsetUserRequest) {
        User newUser= databaseUserService.save(userMapper.requestToUser(upsetUserRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody UpsetUserRequest request) {
        User updatedUser = databaseUserService.update(userMapper.requestToUser(id, request));
        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        databaseUserService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

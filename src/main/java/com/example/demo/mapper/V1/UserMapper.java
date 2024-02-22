package com.example.demo.mapper.V1;

import com.example.demo.model.User;
import com.example.demo.web.model.UpsetUserRequest;
import com.example.demo.web.model.UserListResponse;
import com.example.demo.web.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final CommentMapper commentMapper;

    public User requestToUser(UpsetUserRequest request){
        User user = new User();
        user.setUserName(request.getUserName());

        return user;
    }

    public User requestToUser(Long id, UpsetUserRequest request){
        User user = requestToUser(request);
        user.setId(id);
        return user;
    }

    public UserResponse userToResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUserName(user.getUserName());
        userResponse.setComments(commentMapper.commentListToResponseList(user.getComments()));

        return userResponse;
    }

    public UserListResponse userListToUserListResponse(List<User> clients){
        UserListResponse response = new UserListResponse();

        response.setUsers(clients.stream()
                .map(this::userToResponse).collect(Collectors.toList()));
        return response;
    }
}

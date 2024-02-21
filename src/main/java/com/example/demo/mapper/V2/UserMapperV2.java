package com.example.demo.mapper.V2;

import com.example.demo.model.User;
import com.example.demo.web.model.UpsetUserRequest;
import com.example.demo.web.model.UserListResponse;
import com.example.demo.web.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapperV2.class})
public interface UserMapperV2 {

    public User requestToUser(UpsetUserRequest request);

    @Mapping(target = "id", source = "id")
    public User requestToUser(Long id, UpsetUserRequest request);

    public UserResponse userToResponse(User user);

    default UserListResponse userListToUserListResponse(List<User> users){
        UserListResponse response = new UserListResponse();

        response.setUsers(users.stream()
                .map(this::userToResponse).collect(Collectors.toList()));
        return response;
    }


}

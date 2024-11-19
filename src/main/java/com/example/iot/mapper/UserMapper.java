package com.example.iot.mapper;

import org.mapstruct.Mapper;

import com.example.iot.dto.request.UserCreationRequest;
import com.example.iot.dto.response.UserResponse;
import com.example.iot.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  User toUser(UserCreationRequest request);

  UserResponse toUserResponse(User user);

}

package com.example.usermanagementapi.mappers;


import com.example.usermanagementapi.dtos.UserDto;
import com.example.usermanagementapi.entities.User;
import com.example.usermanagementapi.utils.EntityMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper extends EntityMapper<UserDto, User> {

}

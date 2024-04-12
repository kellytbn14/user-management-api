package com.example.usermanagementapi.mappers;

import com.example.usermanagementapi.dtos.PhoneDto;
import com.example.usermanagementapi.entities.Phone;
import com.example.usermanagementapi.utils.EntityMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {UserMapper.class})
public interface PhoneMapper extends EntityMapper<PhoneDto, Phone> {

}

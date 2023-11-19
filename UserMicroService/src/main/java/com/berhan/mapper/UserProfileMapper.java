package com.berhan.mapper;

import com.berhan.dto.request.CreateUserProfileRequestDto;
import com.berhan.dto.request.UpdateAuthRequestDto;
import com.berhan.dto.request.UpdateUserProfileRequestDto;
import com.berhan.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface UserProfileMapper {
    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    UserProfile userProfileFromCreateUserProfileRequstDto(final CreateUserProfileRequestDto dto);



}


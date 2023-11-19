package com.berhan.mapper;

import com.berhan.dto.request.CreateUserProfileRequestDto;
import com.berhan.dto.request.RegisterAuthRequestDto;
import com.berhan.dto.response.RegisterAuthResponseDto;
import com.berhan.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    @Mapping(source = "id",target = "authId")
    CreateUserProfileRequestDto fromToAuth(final Auth auth);

    Auth fromToRegisterAuthRequestDto(final RegisterAuthRequestDto dto);

    RegisterAuthResponseDto fromToAuth1(final Auth auth);
}

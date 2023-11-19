package com.berhan.service;

import com.berhan.dto.request.CreateUserProfileRequestDto;
import com.berhan.dto.request.UpdateAuthRequestDto;
import com.berhan.dto.request.UpdateUserProfileRequestDto;
import com.berhan.exception.ErrorType;
import com.berhan.exception.UserManagerException;
import com.berhan.manager.AuthManager;
import com.berhan.mapper.UserProfileMapper;
import com.berhan.repository.UserRepository;
import com.berhan.repository.entity.UserProfile;
import com.berhan.utility.JwtTokenManager;
import com.berhan.utility.ServiceManager;
import com.berhan.utility.enums.EStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {

    private final UserRepository repository;
    private final JwtTokenManager tokenManager;
    private final AuthManager authManager;
    public UserProfileService(UserRepository repository, JwtTokenManager tokenManager, AuthManager authManager) {
        super(repository);
        this.repository = repository;
        this.tokenManager = tokenManager;
        this.authManager = authManager;
    }

    public Boolean createUserProfile(CreateUserProfileRequestDto dto){
        try{
            save(UserProfileMapper.INSTANCE.userProfileFromCreateUserProfileRequstDto(dto));
            return true;
        }catch (Exception e){
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }

    }
    public Boolean activationUserProfile(Long authId){
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId);
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return true;
    }

    public Boolean updateUserProfile(UpdateUserProfileRequestDto dto){
        Optional<Long> authId = tokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        userProfile.get().setEmail(dto.getEmail());
        userProfile.get().setAddress(dto.getAddress());
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setAbout(dto.getAbout());
        userProfile.get().setUsername(dto.getUsername());
        userProfile.get().setAvatarUrl(dto.getAvatarUrl());
        if (repository.existsByUsername(dto.getUsername())){
            throw new UserManagerException(ErrorType.USERNAME_DUBLICATE);
        }
        update(userProfile.get());

        UpdateAuthRequestDto updateAuthRequestDto = UpdateAuthRequestDto.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .id(userProfile.get().getAuthId())
                .build();
        authManager.update(updateAuthRequestDto);
        return true;
    }

    public Boolean deleteUserProfile(Long authId){
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId);
        if(userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.DELETED);
        update(userProfile.get());
        return true;
    }


}

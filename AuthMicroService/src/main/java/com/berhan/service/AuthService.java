package com.berhan.service;

import com.berhan.dto.request.LoginAuthRequestDto;
import com.berhan.dto.request.RegisterAuthRequestDto;
import com.berhan.dto.request.UpdateAuthRequestDto;
import com.berhan.dto.response.RegisterAuthResponseDto;
import com.berhan.exception.AuthManagerException;
import com.berhan.exception.ErrorType;
import com.berhan.manager.UserProfileManager;
import com.berhan.mapper.AuthMapper;
import com.berhan.repository.AuthRepository;
import com.berhan.repository.entity.Auth;
import com.berhan.utility.CodeGenerator;
import com.berhan.utility.JwtTokenManager;
import com.berhan.utility.ServiceManager;
import com.berhan.utility.enums.EStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final AuthRepository repository;
    private final JwtTokenManager tokenManager;
    private final UserProfileManager userProfileManager;
    private final AuthMapper authMapper;


    public AuthService(AuthRepository repository, JwtTokenManager tokenManager, UserProfileManager userProfileManager, AuthMapper authMapper) {
        super(repository);
        this.repository = repository;
        this.tokenManager = tokenManager;
        this.userProfileManager = userProfileManager;
        this.authMapper = authMapper;
    }

    public RegisterAuthResponseDto register(RegisterAuthRequestDto dto){
        Auth auth = authMapper.fromToRegisterAuthRequestDto(dto);
        auth.setActivationCode(CodeGenerator.generateCode());
        if (repository.existsByUsername(auth.getUsername())){
            throw new AuthManagerException(ErrorType.USERNAME_DUBLICATE);
        }else if(repository.existsByEmail(auth.getEmail())){
            throw new AuthManagerException(ErrorType.DUBLICATE_EMAIL);
        }
        save(auth);
        userProfileManager.createUserProfile(AuthMapper.INSTANCE.fromToAuth(auth));
        return authMapper.fromToAuth1(auth);
    }

    public Boolean activationStatus(String activationCode){
        Optional<Auth> auth = repository.findOptionalByActivationCode(activationCode);
        if(auth.isEmpty()){
            throw new AuthManagerException(ErrorType.ACTIVATION_CODE_ERROR);
        }
        if (auth.get().getStatus().equals(EStatus.ACTIVE)){
            throw new AuthManagerException(ErrorType.DUBLICATE_ACTIVATION);
        }
        userProfileManager.activationUserProfile(auth.get().getId());
        auth.get().setStatus(EStatus.ACTIVE);
        update(auth.get());
        return true;
    }

    public String login(LoginAuthRequestDto dto){
        Optional<Auth> auth = repository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }else if (!auth.get().getStatus().equals(EStatus.ACTIVE)){
            throw new AuthManagerException(ErrorType.USER_NOT_ACTIVATED);
        }
        String token = tokenManager.createToken(auth.get().getId(),auth.get().getRol()).get();
        if (token == null){
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }
        return token;
    }

    public Boolean update(UpdateAuthRequestDto dto){
        Optional<Auth> auth = repository.findById(dto.getId());
        if(auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setUsername(dto.getUsername());
        auth.get().setEmail(dto.getEmail());
        auth.get().setPassword(dto.getPassword());

        update(auth.get());
        return true;
    }

    public Boolean delete(String token){
        Optional<Auth> auth = repository.findById(tokenManager.getIdFromToken(token).get());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setStatus(EStatus.DELETED);
        update(auth.get());
        userProfileManager.deleteUserProfile(auth.get().getId());
        return true;
    }

}

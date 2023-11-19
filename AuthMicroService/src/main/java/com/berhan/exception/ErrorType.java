package com.berhan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR(5100,"Sunucu hatası....",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parametre hatası....",HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4110,"Kullanıcı veya şifre hatalı....",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4331,"Böyle bir kullanıcı adı bulunamadı",HttpStatus.BAD_REQUEST),
    ACTIVATION_CODE_ERROR(4222,"Aktivasyon Kodu hatalıdır",HttpStatus.BAD_REQUEST),
    USERNAME_DUBLICATE(4111,"Kullanıcı adı kullanılmaktadır",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4214,"Geçersiz token" ,HttpStatus.BAD_REQUEST),
    USER_NOT_ACTIVATED(4258,"Kullanıcı aktive edilmemiş" ,HttpStatus.FORBIDDEN),
    TOKEN_NOT_CREATED(4357,"Token oluşturulamadı" ,HttpStatus.BAD_REQUEST ),
    DUBLICATE_ACTIVATION(4233,"Bu kullanıcı zaten aktif edilmiştir" ,HttpStatus.BAD_REQUEST ),
    DUBLICATE_EMAIL(4787,"Bu email adresi kullanılmaktadır" ,HttpStatus.BAD_REQUEST );

    private int code;
    private String message;
    private HttpStatus httpStatus;
}

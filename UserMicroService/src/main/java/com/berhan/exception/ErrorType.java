package com.berhan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR(5200,"Sunucu hatası....",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4200,"Parametre hatası....",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4211,"Böyle bir kullanıcı adı bulunamadı",HttpStatus.BAD_REQUEST),
    USER_NOT_CREATED(3210,"Kullanıcı profili oluşturulamadı",HttpStatus.BAD_REQUEST),
    USERNAME_DUBLICATE(4210,"Kullanıcı adı kullanılmaktadır",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4213,"Geçersiz token",HttpStatus.BAD_REQUEST),
    TOKEN_NOT_FOUND(4528,"Girilen token geçersizdir" ,HttpStatus.BAD_REQUEST );

    private int code;
    private String message;
    private HttpStatus httpStatus;
}

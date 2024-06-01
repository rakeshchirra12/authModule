package com.rakesh.authmodule.dtos;

import com.rakesh.authmodule.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private Token token;

    public LoginResponseDto(Token token) {
        this.token = token;
    }
}

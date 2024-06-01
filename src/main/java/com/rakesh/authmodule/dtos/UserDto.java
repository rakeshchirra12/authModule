package com.rakesh.authmodule.dtos;

import com.rakesh.authmodule.models.Role;
import com.rakesh.authmodule.models.Token;
import com.rakesh.authmodule.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;
    private Boolean isEmailVerified;

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setRoles(user.getRoles());
        userDto.setIsEmailVerified(user.getIsEmailVerified());
        return userDto;
    }

}

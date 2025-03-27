package com.rakesh.authmodule.security.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rakesh.authmodule.models.Role;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;

    public CustomGrantedAuthority(Role role) {
        this.authority = role.getName();
    }

    public CustomGrantedAuthority() {
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}

package com.compoldata.induflow.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.compoldata.induflow.model.Employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class AuthenticatedUser implements UserDetails{

    private final Employee employee; 

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public String getPassword() {
       return employee.getPassword();
    }

    @Override
    public String getUsername() {
       return employee.getEmployeeEmail();
    }

}

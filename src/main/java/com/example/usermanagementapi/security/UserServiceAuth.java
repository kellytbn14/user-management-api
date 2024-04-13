package com.example.usermanagementapi.security;

import com.example.usermanagementapi.entities.User;
import com.example.usermanagementapi.services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.usermanagementapi.security.Constants.ROLE_ADMIN;
import static com.example.usermanagementapi.security.Constants.ROLE_PREFIX;

@Service
public class UserServiceAuth implements UserDetailsService {

    private final UserService userService;

    public UserServiceAuth(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities());
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + ROLE_ADMIN));
        return authorities;
    }
}

package com.restaurantcontroller.restaurantcontroller.config;

import com.restaurantcontroller.restaurantcontroller.model.UserCredentials;
import com.restaurantcontroller.restaurantcontroller.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Hardcoded admin credentials for demonstration purposes
        if ("ADMIN".equals(username) && "ADMIN".equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
        }

        UserCredentials user = userCredentialsRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // You can add roles/authorities here if needed
        return new UsernamePasswordAuthenticationToken(username, password, Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}


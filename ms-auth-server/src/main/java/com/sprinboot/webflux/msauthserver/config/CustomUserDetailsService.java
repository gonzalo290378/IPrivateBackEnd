package com.sprinboot.webflux.msauthserver.config;

import com.sprinboot.webflux.msauthserver.models.entity.User;
import com.sprinboot.webflux.msauthserver.models.entity.UserDetails.CustomUserDetails;
import com.sprinboot.webflux.msauthserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = userService.findByEmail(email);

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", email);

//        return org.springframework.security.core.userdetails.User
//                .withUsername(userEntity.getUsername())
//                .password(userEntity.getPassword())
//                .authorities("ROLE_USER")
//                .build();

        return new CustomUserDetails(
                userEntity.getUsername(),
                userEntity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                attributes
        );
    }
}

package com.sprinboot.webflux.msauthserver.models.entity.UserDetails;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;
import java.util.Map;

@Getter
public class CustomUserDetails extends User {
    private final Map<String, Object> attributes;

    public CustomUserDetails(String username, String password,
                             Collection<? extends GrantedAuthority> authorities,
                             Map<String, Object> attributes) {
        super(username, password, authorities);
        this.attributes = attributes;
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

}


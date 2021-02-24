package by.alexander.security.security;

import by.alexander.security.entity.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public interface GrantedAuthorityProvider {

    Set<GrantedAuthority> getFrom(Role role);
}

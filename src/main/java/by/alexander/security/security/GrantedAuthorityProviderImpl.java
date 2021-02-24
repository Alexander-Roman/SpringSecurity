package by.alexander.security.security;

import by.alexander.security.entity.Permission;
import by.alexander.security.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public class GrantedAuthorityProviderImpl implements GrantedAuthorityProvider {

    @Override
    public Set<GrantedAuthority> getFrom(Role role) {
        Set<Permission> permissions = role.getPermissions();
        Set<GrantedAuthority> authorities = permissions
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role.name());
        authorities.add(roleAuthority);
        return authorities;
    }
}

package by.alexander.security.security;

import by.alexander.security.entity.Account;
import by.alexander.security.entity.Permission;
import by.alexander.security.entity.Role;
import by.alexander.security.repository.AccountBuilderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountBuilderRepository accountBuilderRepository;

    @Autowired
    public UserDetailsServiceImpl(AccountBuilderRepository accountBuilderRepository) {
        this.accountBuilderRepository = accountBuilderRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account.Builder builder = accountBuilderRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("User %s does not exists", username)
                )
        );

        Account account = builder.build();
        String password = account.getPassword();
        Role role = account.getRole();
        Set<GrantedAuthority> grantedAuthorities = retrieveGrantedAuthoritySetFromRole(role);
        Boolean blocked = account.getBlocked();

        return new User(username, password, !blocked, !blocked, !blocked, !blocked, grantedAuthorities);
    }

    private Set<GrantedAuthority> retrieveGrantedAuthoritySetFromRole(Role role) {
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

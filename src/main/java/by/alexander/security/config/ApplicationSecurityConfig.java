package by.alexander.security.config;

import by.alexander.security.entity.Permission;
import by.alexander.security.entity.Role;
import by.alexander.security.security.GrantedAuthorityProvider;
import by.alexander.security.security.GrantedAuthorityProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ORDER DOES MATTER!!!
        http
                .csrf().disable()
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(Permission.COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/management/api/**").hasAuthority(Permission.COURSE_READ.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/courses")
                .usernameParameter("username") // "email" for example
                .passwordParameter("password")
                .and()
                .rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) // by default 2 weeks
                .key("securityKey") // Used to encrypt RememberMe token
                .rememberMeParameter("remember-me")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");
    }

    @Bean
    public GrantedAuthorityProvider grantedAuthorityProvider() {
        return new GrantedAuthorityProviderImpl();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User
                .builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
//                .roles(Role.ADMIN.name()) //ROLE_ADMIN
                .authorities(grantedAuthorityProvider().getFrom(Role.ADMIN))
                .build();
        UserDetails editor = User
                .builder()
                .username("editor")
                .password(passwordEncoder.encode("editor"))
//                .roles(Role.EDITOR.name()) //ROLE_EDITOR
                .authorities(grantedAuthorityProvider().getFrom(Role.EDITOR))
                .build();
        UserDetails user = User
                .builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
//                .roles(Role.USER.name()) //ROLE_USER
                .authorities(grantedAuthorityProvider().getFrom(Role.USER))
                .build();
        return new InMemoryUserDetailsManager(
                admin,
                editor,
                user
        );
    }
}

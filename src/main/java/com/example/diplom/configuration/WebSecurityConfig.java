package com.example.diplom.configuration;

import com.example.diplom.manager.AuthService;
import com.example.diplom.manager.XimssService;
import com.example.diplom.service.CustomLogoutHandler;
import com.example.diplom.service.RedirectAfterLoginFilter;
import com.example.diplom.service.UserCache;
import com.example.diplom.ximss.request.Login;
import com.example.diplom.ximss.response.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final XimssService ximssService;

    private final AuthService authService;

    private final UserCache userCache;

    private final CustomLogoutHandler logoutHandler;


    public static final String LOGIN_PAGE = "/auth/login";
    public static final String REGISTRATION_PAGE = "/registration";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic()
            .and()
            .csrf().disable()
            .sessionManagement()
            .invalidSessionUrl(LOGIN_PAGE)
            .and()
            .requiresChannel()
            .requestMatchers("/auth/**").requiresSecure()
            .anyRequest().requiresInsecure()
            .and()
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(
                (form) -> form
                    .loginPage("/auth")
                    .loginProcessingUrl("/auth/makeLogin")
                    .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/makeLogout")
                .logoutSuccessUrl("/auth/login?logout")
                .addLogoutHandler(logoutHandler)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll())
            .addFilterAfter(redirectAfterLoginFilter(), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement()
            .maximumSessions(1)
        ;

        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public OncePerRequestFilter redirectAfterLoginFilter() {
        RedirectAfterLoginFilter redirectAfterLoginFilter = new RedirectAfterLoginFilter();
        redirectAfterLoginFilter.setXimssService(ximssService);
        return redirectAfterLoginFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();
            Session session = authService.makeBasicLogin(Login.builder().userName(username).password(password).build());
            if (session.getUrlID() != null) {
                userCache.set(username, session.getUrlID());
                return new UsernamePasswordAuthenticationToken(username, password, List.of(new SimpleGrantedAuthority("ROLE_USER")));
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        };
    }

}

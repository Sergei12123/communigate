package com.example.diplom.configuration;

import com.example.diplom.manager.XimssService;
import com.example.diplom.service.CustomLogoutSuccessHandler;
import com.example.diplom.service.RedirectAfterLoginFilter;
import com.example.diplom.service.RedisRepository;
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

    private final RedisRepository redisRepository;

    private final CustomLogoutSuccessHandler logoutSuccessHandler;

    public static final String LOGIN_PAGE = "/login";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .invalidSessionUrl(LOGIN_PAGE)
                .and()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(LOGIN_PAGE, "/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(
                        (form) -> form
                                .loginPage(LOGIN_PAGE)
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessHandler(logoutSuccessHandler)
                        .logoutUrl("/makeLogout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll())
                .addFilterAfter(redirectAfterLoginFilter(), UsernamePasswordAuthenticationFilter.class)
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
            Session session = ximssService.makeDumbLogin(Login.builder().userName(username).password(password).build());
            if (session.getUrlID() != null) {
                redisRepository.set(username, session.getUrlID());
                return new UsernamePasswordAuthenticationToken(username, password, List.of(new SimpleGrantedAuthority("ROLE_USER")));
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        };
    }


}

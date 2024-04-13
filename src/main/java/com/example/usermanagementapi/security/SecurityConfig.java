package com.example.usermanagementapi.security;


import com.example.usermanagementapi.configs.CORSFilter;
import com.example.usermanagementapi.security.jwt.JwtAuthenticationEntryPoint;
import com.example.usermanagementapi.security.jwt.JwtAuthenticationFilter;
import com.example.usermanagementapi.security.jwt.JwtAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_LIST = {
            "/v3/api-docs/**", "/configuration/ui", "/configuration/security",
            "/swagger-resources/**", "/swagger-ui/index.html", "/swagger-ui.index.html", "/swagger-ui/**", "/v3/api-docs/swagger-config/**",
            "/webjars/**",
            "/user/authenticate", "/user/register", "/h2-console/**"};


    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final UserServiceAuth userServiceAuth;
    private CORSFilter corsFilter;

    @Bean()
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userServiceAuth)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/authenticate");

        return http
                .csrf()
                .disable()
                .headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers(AUTH_LIST).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic().disable()
                .formLogin().disable()
                .build();
    }
}

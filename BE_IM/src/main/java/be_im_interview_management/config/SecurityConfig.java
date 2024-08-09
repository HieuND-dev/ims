package be_im_interview_management.config;

import be_im_interview_management.handler.LoginFailureRestHandler;
import be_im_interview_management.handler.SuccessLoginRestHandler;
import be_im_interview_management.jwt.JwtCheckFilter;
import be_im_interview_management.service.AccountDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 10:30 AM
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final SuccessLoginRestHandler successLoginRestHandler;
    private final LoginFailureRestHandler loginFailureRestHandler;
    private final AccountDetailService accountDetailService;
    private final JwtCheckFilter jwtCheckFilter;
    private final ObjectMapper objectMapper;
    private final CustomLogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(CsrfConfigurer::disable);
        http.authorizeHttpRequests(request ->
                request
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/password/**").permitAll()
                        .requestMatchers("/api/candidates/download/**").permitAll()
                        .anyRequest().authenticated()
        );

        http.userDetailsService(accountDetailService);
        http.formLogin(login -> login
                .loginProcessingUrl("/api/auth/login").permitAll()
                .successHandler(successLoginRestHandler)
                .failureHandler(loginFailureRestHandler)
        );

        http.sessionManagement(sessionConfig -> sessionConfig
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling(eh -> eh.authenticationEntryPoint(
                (request, response, e) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getOutputStream().println(objectMapper.writeValueAsString(
                            Map.of("status", HttpServletResponse.SC_UNAUTHORIZED,
                                    "message", e.getMessage())
                    ));
                }
        ));

        http.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout(l->l.logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(
                        (request, response, authentication) -> SecurityContextHolder.clearContext()
                ));

        return http.build();

    }


}

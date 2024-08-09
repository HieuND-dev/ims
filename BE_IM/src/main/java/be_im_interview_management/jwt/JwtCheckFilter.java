package be_im_interview_management.jwt;

import be_im_interview_management.entities.Token;
import be_im_interview_management.service.domainService.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 10:24 AM
 */
@Component
@RequiredArgsConstructor
public class JwtCheckFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            var username = jwtProvider.verifyAndExtractUser(jwt);
            var userDetails = userDetailsService.loadUserByUsername(username);
            boolean isLoggedOutToken = tokenService.findByToken(jwt)
                    .map(Token::isLoggedOut).orElse(true);

            if (userDetails == null || !userDetails.isEnabled()  || isLoggedOutToken) {
                throw new UsernameNotFoundException("User " + username + " not found or not valid");
            }

            var authentication = UsernamePasswordAuthenticationToken.authenticated(userDetails, jwt,
                    userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }

    }
}

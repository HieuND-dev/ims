package be_im_interview_management.config;

import be_im_interview_management.entities.Token;
import be_im_interview_management.service.domainService.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * Created by: HieuND64
 * Date Time: 7/27/2024 10:11 PM
 */
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenService tokenService;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return;
        }

        String token = authHeader.substring(7);

        Token storedToken = tokenService.findByToken(token).orElse(null);

        assert storedToken != null;
        storedToken.setLoggedOut(true);
        tokenService.save(storedToken);
    }
}

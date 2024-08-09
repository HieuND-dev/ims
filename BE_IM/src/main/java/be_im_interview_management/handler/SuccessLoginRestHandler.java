package be_im_interview_management.handler;

import be_im_interview_management.entities.Account;
import be_im_interview_management.entities.Token;
import be_im_interview_management.jwt.JwtProvider;
import be_im_interview_management.repositories.AccountRepository;
import be_im_interview_management.repositories.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 10:29 AM
 */
@Component
@RequiredArgsConstructor
public class SuccessLoginRestHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;
    private final TokenRepository tokenRepository;
    private final AccountRepository accountRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        var jwt = jwtProvider.generateAccessToken(authentication.getName());

        Account account = accountRepository.findByEmail(authentication.getName()).orElse(null);

        assert account != null;

        Token token = Token.builder()
                .token(jwt)
                .isLoggedOut(false)
                .account(account)
                .build();

        List<Token> validTokenListByAccount = tokenRepository.findAllTokensByAccount(account.getId());
        if (!validTokenListByAccount.isEmpty()) {
            validTokenListByAccount.forEach(t ->
                    t.setLoggedOut(true));
        }

        tokenRepository.saveAll(validTokenListByAccount);

        tokenRepository.save(token);

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(Map.of(
                        "token", jwt,
                        "email", account.getEmail(),
                        "username", account.getUsername(),
                        "department",account.getDepartment(),
                        "roles", account.getRole(),
                        "id", account.getId()
                )));
    }
}

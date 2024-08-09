package be_im_interview_management.service.domainServiceImpl;

import be_im_interview_management.entities.Token;
import be_im_interview_management.repositories.BaseRepository;
import be_im_interview_management.repositories.TokenRepository;
import be_im_interview_management.service.domainService.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/27/2024 3:35 PM
 */
@Service
public class TokenServiceImpl extends BaseServiceImpl<Token, Long> implements TokenService {
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        super(tokenRepository);
        this.tokenRepository = tokenRepository;
    }

    @Override
    public List<Token> findAllTokensByAccount(Long accountId) {
        return tokenRepository.findAllTokensByAccount(accountId);
    }

    @Override
    public void saveAll(List<Token> tokens) {
        tokenRepository.saveAll(tokens);
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}

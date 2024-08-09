package be_im_interview_management.service.domainService;

import be_im_interview_management.entities.Token;

import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/27/2024 3:35 PM
 */
public interface TokenService extends BaseService<Token, Long>{

    List<Token> findAllTokensByAccount(Long accountId);

    void saveAll(List<Token> tokens);

    Optional<Token> findByToken(String token);
}

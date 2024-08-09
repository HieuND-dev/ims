package be_im_interview_management.repositories;

import be_im_interview_management.entities.Token;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/27/2024 8:54 AM
 */
@Repository
public interface TokenRepository extends BaseRepository<Token, Long>{

    @Query("""
            select t from Token t inner join Account a
            on t.account.id = a.id
            where t.account.id = :accountId and t.isLoggedOut = false 
    """)
    List<Token> findAllTokensByAccount(Long accountId);

    Optional<Token> findByToken(String token);
}

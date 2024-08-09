package be_im_interview_management.repositories;

import be_im_interview_management.entities.PasswordResetToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/28/2024 11:21 PM
 */
@Repository
public interface PasswordResetTokenRepository extends BaseRepository<PasswordResetToken, Long>{

    Optional<PasswordResetToken> findByToken(String token);
}

package be_im_interview_management.service.domainService;

import be_im_interview_management.entities.PasswordResetToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/28/2024 11:20 PM
 */

public interface PasswordResetTokenService extends BaseService<PasswordResetToken, Long>{
    Optional<PasswordResetToken> findByToken(String token);
}

package be_im_interview_management.service.domainServiceImpl;

import be_im_interview_management.entities.PasswordResetToken;
import be_im_interview_management.repositories.BaseRepository;
import be_im_interview_management.repositories.PasswordResetTokenRepository;
import be_im_interview_management.service.domainService.PasswordResetTokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/28/2024 11:21 PM
 */
@Service
public class PasswordResetTokenServiceImpl extends BaseServiceImpl<PasswordResetToken, Long>
        implements PasswordResetTokenService {
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRep) {
        super(passwordResetTokenRep);
        this.passwordResetTokenRepository = passwordResetTokenRep;
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
}

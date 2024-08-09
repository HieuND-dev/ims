package be_im_interview_management.util;

import be_im_interview_management.config.SpringAuditorAware;
import be_im_interview_management.entities.Account;
import be_im_interview_management.exception.EmailAlreadyExistException;
import be_im_interview_management.service.domainService.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 8/5/2024 10:50 AM
 */
@Component
@RequiredArgsConstructor
public class SecurityUtil {
    private final AccountService accountService;

    public Account getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (email.isEmpty()) {
            return null;
        } else {
            return accountService
                    .findByEmail(email)
                    .orElseThrow(EmailAlreadyExistException::new);
        }
    }
}

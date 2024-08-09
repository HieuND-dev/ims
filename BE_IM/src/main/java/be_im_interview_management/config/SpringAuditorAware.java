package be_im_interview_management.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 9:31 AM
 */
@Component
public class SpringAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .flatMap(auth -> Optional.ofNullable(auth.getName()));
    }
}

package be_im_interview_management.service;


import be_im_interview_management.entities.Account;
import be_im_interview_management.model.UserDetailsImpl;
import be_im_interview_management.service.domainService.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 10:32 AM
 */
@Service
@RequiredArgsConstructor
public class AccountDetailService implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Account account = accountService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new UserDetailsImpl(account);
    }
}

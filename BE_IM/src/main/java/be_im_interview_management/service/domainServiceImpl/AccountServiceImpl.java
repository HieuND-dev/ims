package be_im_interview_management.service.domainServiceImpl;


import be_im_interview_management.entities.Account;
import be_im_interview_management.enums.Constant;
import be_im_interview_management.enums.Role;
import be_im_interview_management.exception.EmailAlreadyExistException;
import be_im_interview_management.exception.ResourceNotFoundException;
import be_im_interview_management.repositories.AccountRepository;
import be_im_interview_management.service.EmailService;
import be_im_interview_management.service.domainService.AccountService;
import jakarta.mail.MessagingException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 9:41 AM
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Long> implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder, @Lazy EmailService emailService) {
        super(accountRepository);
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    private String generateRandomPassword() {
        Random random = new SecureRandom();
        StringBuilder password = new StringBuilder(Constant.PASSWORD_LENGTH);
        for (int i = 0; i < Constant.PASSWORD_LENGTH; i++) {
            password.append(Constant.CHARACTERS.charAt(random.nextInt(Constant.CHARACTERS.length())));
        }
        return password.toString();
    }

    private String genUsername(String fullname) {
        String[] names = fullname.split(" ");
        String firstName = names[names.length - 1];
        StringBuilder initials = new StringBuilder();

        for (int i = 0; i < names.length - 1; i++) {
            initials.append(names[i].charAt(0));
        }

        String baseUsername = firstName + initials.toString();

        int count = 1;
        while (accountRepository.existsByUsername(baseUsername + count)) {
            count++;
        }

        return baseUsername + count;
    }

    public String createUser(Account account){
        if (checkExistedEmail(account.getEmail())) {
            throw new EmailAlreadyExistException();
        }

        String generatedPassword = generateRandomPassword();
        String encoder = passwordEncoder.encode(generatedPassword);
        account.setPassword(encoder);

        account.setUsername(genUsername(account.getFullName()));

        accountRepository.save(account);
        return generatedPassword;
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account updateAccount(Long id, Account updatedAccount) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setFullName(updatedAccount.getFullName());
                    account.setUsername(genUsername(updatedAccount.getFullName()));
                    account.setEmail(updatedAccount.getEmail());
                    account.setDateOfBirth(updatedAccount.getDateOfBirth());
                    account.setAddress(updatedAccount.getAddress());
                    account.setPhoneNo(updatedAccount.getPhoneNo());
                    account.setGender(updatedAccount.getGender());
                    account.setRole(updatedAccount.getRole());
                    account.setDepartment(updatedAccount.getDepartment());
                    account.setNote(updatedAccount.getNote());
                    return accountRepository.save(account);
                }).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public String forgotPassword(String email) {
        Account account = findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("Account not found with this email " + email));

        try {
            emailService.sendSetPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send set password email. ");
        }

        return "We've sent an email with the link to reset your password.";
    }

    @Override
    public void updatePassword(Account account, String newPassword) {
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public List<String> viewUsernamesWithRole(Role role) {
        return accountRepository.findUsernamesByRole(role);
    }

    @Override
    public boolean checkExistedEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (passwordEncoder.matches(oldPassword, account.getPassword())) {
            account.setPassword(passwordEncoder.encode(newPassword));
            accountRepository.save(account);
            return true;
        } else {
            return false;
        }
    }



    @Override
    public Page<Account> getAllAccount(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo -1,6);
        return accountRepository.findAll(pageable);
    }
}

package be_im_interview_management.service.domainService;



import be_im_interview_management.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import be_im_interview_management.enums.Role;

import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 9:40 AM
 */
public interface AccountService extends BaseService<Account, Long> {
    Optional<Account> findByEmail(String email);

    String createUser(Account account);

    Account updateAccount(Long id, Account updatedAccount);

    String forgotPassword(String email);

    void updatePassword(Account account, String newPassword);

    Optional<Account> findByUsername(String username);

    Page<Account> getAllAccount(Integer pageNo);

    List<String> viewUsernamesWithRole(Role role);

    boolean checkExistedEmail(String email);

    boolean changePassword(Long id, String oldPassword, String newPassword);
}

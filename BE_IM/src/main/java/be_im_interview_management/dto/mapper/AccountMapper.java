package be_im_interview_management.dto.mapper;

import be_im_interview_management.dto.AccountDTO;
import be_im_interview_management.entities.Account;
import org.springframework.stereotype.Component;

/**
 * Created by: HieuND64
 * Date Time: 7/28/2024 10:31 AM
 */
@Component
public class AccountMapper {
//    public Account toAccount(AccountDTO accountDTO) {
//        return new Account();
//    }

    public AccountDTO toAccountDTO(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getUsername(),
                account.getEmail(),
                account.getPhoneNo(),
                account.getRole(),
                account.getAccountStatus(),
                account.getFullName(),
                account.getDateOfBirth(),
                account.getAddress(),
                account.getGender(),
                account.getDepartment(),
                account.getNote());
    }
}

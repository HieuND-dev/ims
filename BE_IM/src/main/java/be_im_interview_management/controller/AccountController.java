package be_im_interview_management.controller;

import be_im_interview_management.dto.AccountDTO;
import be_im_interview_management.dto.ChangePasswordRequestDTO;
import be_im_interview_management.dto.mapper.AccountMapper;
import be_im_interview_management.entities.Account;
import be_im_interview_management.enums.AccountStatus;
import be_im_interview_management.enums.Constant;
import be_im_interview_management.enums.Role;
import be_im_interview_management.exception.ResourceNotFoundException;
import be_im_interview_management.service.EmailService;

import be_im_interview_management.service.domainService.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Secured(Constant.ADMIN_ROLE)
@RequestMapping("/api/users")
public class AccountController {

	private final AccountService accountService;
	private final EmailService emailService;
	private final AccountMapper accountMapper;
	private final PasswordEncoder passwordEncoder;


	@PostMapping("/add")
	public ResponseEntity<String> addAccount(@RequestBody Account account){
		String password = accountService.createUser(account);

		emailService.sendEmail(account.getEmail(), account.getEmail(), password);
		return ResponseEntity.status(HttpStatus.CREATED).body("Successfully new account");
	}

	@GetMapping("/viewList")
	public ResponseEntity<List<AccountDTO>> viewUserList() {
		Sort sort = Sort.by(Sort.Order.asc("accountStatus"),
				Sort.Order.asc("role"),
				Sort.Order.asc("username"));

		return ResponseEntity.ok(
				accountService.findAll(Optional.of(sort))
				.stream()
				.map(accountMapper::toAccountDTO)
				.collect(Collectors.toList()));
	}

	@GetMapping("/details/{id}")
	public ResponseEntity<AccountDTO> viewUserDetails(@PathVariable Long id) {
		return ResponseEntity.ok(accountMapper
				.toAccountDTO(accountService.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException(id))));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AccountDTO> updateAccount( @PathVariable Long id,
													 @RequestBody Account updateAccount) {
		Account account = accountService.updateAccount(id, updateAccount);
		return ResponseEntity.ok(accountMapper.toAccountDTO(account));
	}

	@PutMapping("/deactivate/{id}")
	public ResponseEntity<Void> deactivateAccount(@PathVariable Long id) {
		Account account = accountService
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));

		if(account.getAccountStatus() == AccountStatus.ACTIVE) {
			account.setAccountStatus(AccountStatus.INACTIVE);
			accountService.save(account);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
	}

	@PutMapping("/activate/{id}")
    public ResponseEntity<Void> activateAccount(@PathVariable Long id) {
		Account account = accountService
				.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if(account.getAccountStatus() == AccountStatus.INACTIVE) {
			account.setAccountStatus(AccountStatus.ACTIVE);
			accountService.save(account);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
	}

	@Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE, Constant.INTERVIEWER_ROLE})
	@GetMapping(params = "role")
	public ResponseEntity<List<String>> viewUsernamesWithRole(@RequestParam Role role) {
		return ResponseEntity.ok(accountService.viewUsernamesWithRole(role));
	}
	@GetMapping("/all")
	public ResponseEntity<Page<Account>> getAllAccounts
			(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		Page<Account> accountPage = accountService.getAllAccount(pageNo);
		if(pageNo < accountPage.getTotalPages() || pageNo < 1) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(accountPage);
	}


	@Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE, Constant.INTERVIEWER_ROLE})
	@PatchMapping("/change-password/{id}")
	public ResponseEntity<String> changePassword(@PathVariable Long id,
												 @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {

		if (accountService.changePassword(id, changePasswordRequestDTO.oldPassword(), changePasswordRequestDTO.newPassword())) {
			return ResponseEntity.ok("Password changed successfully");
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Old password is incorrect");
		}
    }
}


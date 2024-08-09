package be_im_interview_management.controller;

import be_im_interview_management.dto.PasswordRequestDTO;
import be_im_interview_management.entities.Account;
import be_im_interview_management.entities.PasswordResetToken;
import be_im_interview_management.service.domainService.AccountService;
import be_im_interview_management.service.domainService.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Created by: HieuND64
 * Date Time: 7/28/2024 8:50 PM
 */
@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {

    private final AccountService accountService;
    private final PasswordResetTokenService passwordResetTokenService;

    @PutMapping("forgot")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return ResponseEntity.ok(accountService.forgotPassword(email));
    }

    @GetMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token has expired");
        }

        if (passwordResetToken.isUsed()) {
            return ResponseEntity.badRequest().body("Token has already been used");
        }

        return ResponseEntity.ok("Token is valid, proceed with password reset");
    }

    @PutMapping("/reset")
    public ResponseEntity<String> updatePassword(@RequestParam String token, @RequestBody PasswordRequestDTO passwordRequestDTO) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token has expired");
        }

        if (passwordResetToken.isUsed()) {
            return ResponseEntity.badRequest().body("Token has already been used");
        }

        Account account = passwordResetToken.getAccount();
        accountService.updatePassword(account, passwordRequestDTO.newPassword());

        passwordResetToken.setUsed(true);
        passwordResetTokenService.save(passwordResetToken);

        return ResponseEntity.ok("Password has been reset successfully");
    }
}

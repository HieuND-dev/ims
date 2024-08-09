package be_im_interview_management.service;

import be_im_interview_management.entities.Account;
import be_im_interview_management.entities.Offer;
import be_im_interview_management.entities.PasswordResetToken;
import be_im_interview_management.enums.Constant;
import be_im_interview_management.service.domainService.AccountService;
import be_im_interview_management.service.domainService.PasswordResetTokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class EmailService {
	private final JavaMailSender mailSender;
	private final AccountService accountService;
	private final PasswordResetTokenService passwordResetTokenService;


	public void sendEmail(String toEmail, String username, String password) {
		String subject = "no-reply-email-IMS-system <Account created>";
		String body = String.format(Constant.EMAIL_TEMPLATE_CREATE_ACCOUNT,username, password);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}

	public void sendSetPasswordEmail(String email) throws MessagingException {

		Account account = accountService.findByEmail(email)
				.orElseThrow(
						() -> new RuntimeException("Account not found with this email " + email));

		String token = UUID.randomUUID().toString();
		PasswordResetToken passwordResetToken = PasswordResetToken
				.builder()
				.token(token)
				.account(account)
				.expiryDate(LocalDateTime.now().plusDays(Constant.LINK_EXPIRY_TIME))
				.build();
		passwordResetTokenService.save(passwordResetToken);

		String subject = "Password Reset";

		String template = Constant.EMAIL_TEMPLATE_SET_PASSWORD;
		String body = template.formatted(email, token);

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(email);
		helper.setSubject(subject);
		helper.setText(body, true);

		mailSender.send(message);
	}


	public void sendOfferReminderEmail(String email, Offer offer) throws MessagingException {
		String subject = "no-reply-email-IMS-system <Take action on Job offer>";

		String template = Constant.EMAIL_TEMPLATE_OFFER_REMINDER;
		String body = template.formatted(offer.getInterviewSchedule().getCandidate().getFullName(),
				offer.getPosition().getLabel(),
				offer.getDueDate(),
				offer.getId(),
				offer.getRecruiterOwner().getUsername());

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(email);
		helper.setSubject(subject);
		helper.setText(body, true);

		mailSender.send(message);

	}



}

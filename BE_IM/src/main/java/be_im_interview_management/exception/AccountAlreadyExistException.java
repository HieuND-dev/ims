package be_im_interview_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AccountAlreadyExistException extends RuntimeException {
  public AccountAlreadyExistException(String message) {
    super(message);
  }
}

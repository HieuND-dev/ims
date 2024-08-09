package be_im_interview_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PermissionRequiredException extends RuntimeException {
  public PermissionRequiredException() {
    super("You do not have permission to access this");
  }
}

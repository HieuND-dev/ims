package be_im_interview_management.exception;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(Object id) {
    super("Resource not found: (id=%s)".formatted(id));
  }
}

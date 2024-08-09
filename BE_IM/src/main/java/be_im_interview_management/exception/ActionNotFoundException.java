package be_im_interview_management.exception;

/**
 * Created by: HieuND64
 * Date Time: 7/30/2024 3:44 PM
 */
public class ActionNotFoundException extends RuntimeException{
    public ActionNotFoundException(String action) {
        super("Invalid action: " + action);
    }
}

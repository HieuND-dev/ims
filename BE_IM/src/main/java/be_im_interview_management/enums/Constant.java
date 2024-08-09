package be_im_interview_management.enums;

/**
 * Created by: HieuND64
 * Date Time: 7/26/2024 10:09 AM
 */
public class Constant {
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String RECRUITER_ROLE = "RECRUITER";
    public static final String MANAGER_ROLE = "MANAGER";
    public static final String INTERVIEWER_ROLE = "INTERVIEWER";

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static final int PASSWORD_LENGTH = 12;

    public static final int LINK_EXPIRY_TIME = 1;

    public static final String EMAIL_TEMPLATE_CREATE_ACCOUNT = """
            This email is from IMS system,
            Your account has been created. Please use the following credential to login:
            • User name: %s
            • Password: %s
            If anything wrong, please reach-out recruiter <offer recruiter owner account>. We are so sorry for this inconvenience.
            Thanks & Regards!
            IMS Team.""";

    public static final String EMAIL_TEMPLATE_SET_PASSWORD = """
            We have just received a password reset request for %s. <br>
            Please click here to reset your password: http://localhost:5173/reset-password?token=%s <br>
            For your security, the link will expire in 24 hours or immediately after you reset your password. <br>
            Thanks & Regards! <br>
            IMS Team.""";

    public static final String EMAIL_TEMPLATE_OFFER_REMINDER = """
            This email is from IMS system,
            You have an offer to take action for Candidate %s position %s before %s, the contract is attached with this no-reply-email.
            Please refer to this link to take action: http://localhost:5173/offer/details/%d
            If anything wrong, please reach out to recruiter %s. We are so sorry for this inconvenience.
            Thanks & Regards!
            IMS Team.""";

    public static final String FOLDER_PATH = "D:\\FileCV\\";
}

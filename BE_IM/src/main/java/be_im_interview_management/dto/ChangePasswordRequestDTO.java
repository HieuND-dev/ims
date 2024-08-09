package be_im_interview_management.dto;

/**
 * Created by: HieuND64
 * Date Time: 8/4/2024 10:47 AM
 */

public record ChangePasswordRequestDTO (
        String oldPassword,
        String newPassword
) {
}

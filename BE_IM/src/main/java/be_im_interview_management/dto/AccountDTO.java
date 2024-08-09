package be_im_interview_management.dto;

import be_im_interview_management.enums.AccountStatus;
import be_im_interview_management.enums.Department;
import be_im_interview_management.enums.Gender;
import be_im_interview_management.enums.Role;

import java.time.LocalDate;

/**
 * Created by: HieuND64
 * Date Time: 7/26/2024 10:06 AM
 */
public record AccountDTO(
        Long id,
        String username,
        String email,
        String phoneNo,
        Role role,
        AccountStatus status,
        String fullName,
        LocalDate dateOfBirth,
        String address,
        Gender gender,
        Department department,
        String note
) {
}

package be_im_interview_management.dto;

import be_im_interview_management.enums.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: HieuND64
 * Date Time: 7/29/2024 3:53 PM
 */
public record CandidateResponseDTO(
        Long id,
        String fullName,
        LocalDate dateOfBirth,
        String phoneNo,
        String address,
        String email,
        Gender gender,
        byte[] fileCV,
        String cvName,
        Position position,
        List<Skill> skills,
        String note,
        CandidateStatus candidateStatus,
        Integer yearOfExperience,
        HighestLevel highestLevel,
        String ownerHR,
        String timeLabel
) {
}

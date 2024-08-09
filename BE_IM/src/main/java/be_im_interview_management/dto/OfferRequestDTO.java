package be_im_interview_management.dto;

import be_im_interview_management.enums.*;

import java.time.LocalDate;

/**
 * Created by: HieuND64
 * Date Time: 7/30/2024 10:04 AM
 */
public record OfferRequestDTO(
        Long id,
        String interviewTitle,
        String candidateName,
        String interviewNote,
        Position position,
        OfferStatus status,
        ContractType contractType,
        Level level,
        Department department,
        LocalDate contractFrom,
        LocalDate contractTo,
        LocalDate dueDate,
        Double basicSalary,
        String offerNote,
        String approver,
        String recruiterOwner,
        String interviewInfo,
        String interviewer,
        String email,
        String timeLabel,
        CandidateStatus candidateStatus
) {
}

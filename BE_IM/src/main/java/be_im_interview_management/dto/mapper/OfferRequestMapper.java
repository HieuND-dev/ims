package be_im_interview_management.dto.mapper;

import be_im_interview_management.dto.OfferRequestDTO;
import be_im_interview_management.entities.Account;
import be_im_interview_management.entities.InterviewSchedule;
import be_im_interview_management.entities.Offer;
import be_im_interview_management.service.domainService.AccountService;
import be_im_interview_management.service.domainService.InterviewScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * Created by: HieuND64
 * Date Time: 7/30/2024 10:17 AM
 */
@Component
@RequiredArgsConstructor
public class OfferRequestMapper {
    private final InterviewScheduleService interviewScheduleService;
    private final AccountService accountService;

    public Offer toOffer(OfferRequestDTO offerRequestDTO) {
        InterviewSchedule interviewSchedule = interviewScheduleService
                .findByScheduleTitle(offerRequestDTO.interviewTitle())
                .orElseThrow(() -> new RuntimeException("Interview schedule not found"));

        Account approver = accountService.findByUsername(offerRequestDTO.approver())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));

        Account recruiterOwner = accountService.findByUsername(offerRequestDTO.recruiterOwner())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));

        return Offer.builder()
                .id(offerRequestDTO.id())
                .position(offerRequestDTO.position())
                .status(offerRequestDTO.status())
                .contractType(offerRequestDTO.contractType())
                .level(offerRequestDTO.level())
                .contractFrom(offerRequestDTO.contractFrom())
                .contractTo(offerRequestDTO.contractTo())
                .department(offerRequestDTO.department())
                .dueDate(offerRequestDTO.dueDate())
                .basicSalary(offerRequestDTO.basicSalary())
                .note(offerRequestDTO.offerNote())
                .interviewSchedule(interviewSchedule)
                .approver(approver)
                .recruiterOwner(recruiterOwner)
                .build();
    }

    public OfferRequestDTO toOfferRequestDTO(Offer offer) {
        String interviewInfo = String.format("""
                Interview: %s
                Interviewer: %s
                """,
                offer.getInterviewSchedule().getScheduleTitle(),
                offer.getInterviewSchedule().getInterviewer().getUsername());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String createdDateTimeString = offer.getCreatedDate().format(formatter);
        String updatedDateTimeString = offer.getLastModifiedDate().format(formatter);
        String updatedBy = accountService
                .findByEmail(offer.getLastModifiedBy())
                .map(Account::getUsername)
                .orElse(null);

        String timeLabel = String.format("Created on %s, last updated by %s on %s", createdDateTimeString, updatedBy, updatedDateTimeString);

        return new OfferRequestDTO(
                offer.getId(),
                offer.getInterviewSchedule().getScheduleTitle(),
                offer.getInterviewSchedule().getCandidate().getFullName(),
                offer.getInterviewSchedule().getNote(),
                offer.getPosition(),
                offer.getStatus(),
                offer.getContractType(),
                offer.getLevel(),
                offer.getDepartment(),
                offer.getContractFrom(),
                offer.getContractTo(),
                offer.getDueDate(),
                offer.getBasicSalary(),
                offer.getNote(),
                offer.getApprover().getUsername(),
                offer.getRecruiterOwner().getUsername(),
                interviewInfo,
                offer.getInterviewSchedule().getInterviewer().getUsername(),
                offer.getInterviewSchedule().getCandidate().getEmail(),
                timeLabel,
                offer.getInterviewSchedule().getCandidate().getCandidateStatus()
        );
    }
}

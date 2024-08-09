package be_im_interview_management.dto.mapper;

import be_im_interview_management.dto.CandidateResponseDTO;
import be_im_interview_management.entities.Account;
import be_im_interview_management.entities.Candidate;
import be_im_interview_management.enums.Constant;
import be_im_interview_management.service.domainService.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;

/**
 * Created by: HieuND64
 * Date Time: 7/29/2024 4:01 PM
 */
@Component
@RequiredArgsConstructor
public class CandidateResponseMapper {
    private final AccountService accountService;


    public CandidateResponseDTO toCandidateResponseDTO(Candidate candidate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String createdDateTimeString = candidate.getCreatedDate().format(formatter);
        String updatedDateTimeString = candidate.getLastModifiedDate().format(formatter);
        String updatedBy = accountService
                .findByEmail(candidate.getLastModifiedBy())
                .map(Account::getUsername)
                .orElse(null);

        String timeLabel = String.format("Created on %s, last updated by %s on %s", createdDateTimeString, updatedBy, updatedDateTimeString);

        byte[] fileCV;
        try {
            fileCV = Files.readAllBytes(new File(Constant.FOLDER_PATH + candidate.getCvFileName()).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new CandidateResponseDTO(
                candidate.getId(),
                candidate.getFullName(),
                candidate.getDateOfBirth(),
                candidate.getPhoneNo(),
                candidate.getAddress(),
                candidate.getEmail(),
                candidate.getGender(),
                fileCV,
                candidate.getCvFileName(),
                candidate.getPosition(),
                candidate.getSkills(),
                candidate.getNote(),
                candidate.getCandidateStatus(),
                candidate.getYearOfExperience(),
                candidate.getHighestLevel(),
                candidate.getRecruiter().getUsername(),
                timeLabel
        );
    }
}

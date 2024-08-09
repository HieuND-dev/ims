package be_im_interview_management.dto.mapper;

import be_im_interview_management.dto.CandidateRequestDTO;
import be_im_interview_management.entities.Account;
import be_im_interview_management.entities.Candidate;
import be_im_interview_management.service.domainService.AccountService;
import be_im_interview_management.service.domainService.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by: HieuND64
 * Date Time: 7/29/2024 4:01 PM
 */
@Component
@RequiredArgsConstructor
public class CandidateRequestMapper {
    private final AccountService accountService;
    private final CandidateService candidateService;

    public Candidate toCandidate(CandidateRequestDTO candidateRequestDTO) throws IOException {
        Account recruiter = accountService
                .findByUsername(candidateRequestDTO.ownerHR())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));

        return Candidate.builder()
                .id(candidateRequestDTO.id())
                .fullName(candidateRequestDTO.fullName())
                .dateOfBirth(candidateRequestDTO.dateOfBirth())
                .phoneNo(candidateRequestDTO.phoneNo())
                .address(candidateRequestDTO.address())
                .email(candidateRequestDTO.email())
                .gender(candidateRequestDTO.gender())
                .cvFileName(candidateService.uploadCVToFileSystem(candidateRequestDTO.fileCV()))
                .position(candidateRequestDTO.position())
                .skills(candidateRequestDTO.skills())
                .note(candidateRequestDTO.note())
                .candidateStatus(candidateRequestDTO.candidateStatus())
                .yearOfExperience(candidateRequestDTO.yearOfExperience())
                .highestLevel(candidateRequestDTO.highestLevel())
                .recruiter(recruiter)
                .build();
    }

}

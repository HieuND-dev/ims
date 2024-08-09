package be_im_interview_management.service.domainServiceImpl;


import be_im_interview_management.dto.CandidateRequestDTO;
import be_im_interview_management.dto.ValidCandidateInputDTO;
import be_im_interview_management.entities.Candidate;
import be_im_interview_management.enums.CandidateStatus;
import be_im_interview_management.enums.Constant;
import be_im_interview_management.exception.ResourceNotFoundException;
import be_im_interview_management.repositories.AccountRepository;
import be_im_interview_management.repositories.CandidateRepository;
import be_im_interview_management.service.domainService.CandidateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 3:22 PM
 */
@Service
public class CandidateServiceImpl extends BaseServiceImpl<Candidate, Long> implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final AccountRepository accountRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository, AccountRepository accountRepository) {
        super(candidateRepository);
        this.candidateRepository = candidateRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Candidate> findAllOrderedByStatusAndCreatedDate() {
        return candidateRepository.findAllOrderedByStatusAndCreatedDate();
    }

    @Override
    public Candidate updateCandidate(Long id, CandidateRequestDTO candidateRequestDTO) {
        return candidateRepository.findById(id)
                .map(candidate -> {
                    String fileCV;
                    try {
                        fileCV = uploadCVToFileSystem(candidateRequestDTO.fileCV());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    candidate.setFullName(candidateRequestDTO.fullName());
                    candidate.setDateOfBirth(candidateRequestDTO.dateOfBirth());
                    candidate.setEmail(candidateRequestDTO.email());
                    candidate.setPhoneNo(candidateRequestDTO.phoneNo());
                    candidate.setAddress(candidateRequestDTO.address());
                    candidate.setGender(candidateRequestDTO.gender());
                    candidate.setPosition(candidateRequestDTO.position());
                    candidate.setSkills(candidateRequestDTO.skills());
                    candidate.setNote(candidateRequestDTO.note());
                    candidate.setCvFileName(fileCV);
                    candidate.setCandidateStatus(candidateRequestDTO.candidateStatus());
                    candidate.setYearOfExperience(candidateRequestDTO.yearOfExperience());
                    candidate.setHighestLevel(candidateRequestDTO.highestLevel());
                    candidate.setRecruiter(accountRepository
                            .findByUsername(candidateRequestDTO.ownerHR())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid username")));
                    return candidateRepository.save(candidate);
                }).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public String uploadCVToFileSystem(MultipartFile file) throws IOException {
        String filePath = Constant.FOLDER_PATH + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        return file.getOriginalFilename();
    }

    @Override
    public byte[] downloadCVFromFileSystem(String fileName) throws IOException {
        return Files.readAllBytes(new File(Constant.FOLDER_PATH + fileName).toPath());
    }

    @Override
    public List<ValidCandidateInputDTO> getCandidatesByStatus(CandidateStatus candidateStatus) {
        return candidateRepository.findAllByCandidateStatus(candidateStatus)
                .stream()
                .map(c -> new ValidCandidateInputDTO(c.getFullName(), c.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkExistedEmail(String email) {
        return candidateRepository.existsByEmail(email);
    }

    @Override
    public Page<Candidate> getAllOrderByCandidateStatusAndDate(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo -1, 10);
        return candidateRepository.getAllOrderByCandidateStatusAndDate(pageable);
    }

    @Override
    public Optional<Candidate> findByEmail(String email) {
        return candidateRepository.findByEmail(email);
    }

}

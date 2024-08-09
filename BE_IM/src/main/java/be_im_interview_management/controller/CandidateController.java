package be_im_interview_management.controller;

import be_im_interview_management.dto.CandidateRequestDTO;
import be_im_interview_management.dto.CandidateResponseDTO;
import be_im_interview_management.dto.ValidCandidateInputDTO;
import be_im_interview_management.dto.mapper.CandidateRequestMapper;
import be_im_interview_management.dto.mapper.CandidateResponseMapper;
import be_im_interview_management.entities.Candidate;
import be_im_interview_management.enums.CandidateStatus;
import be_im_interview_management.enums.Constant;
import be_im_interview_management.exception.EmailAlreadyExistException;
import be_im_interview_management.exception.ResourceNotFoundException;
import be_im_interview_management.service.domainService.CandidateService;
import be_im_interview_management.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by: HieuND64
 * Date Time: 7/29/2024 3:36 PM
 */
@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;
    private final CandidateRequestMapper candidateRequestMapper;
    private final CandidateResponseMapper candidateReMapper;
    private final CandidateResponseMapper candidateResponseMapper;

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE, Constant.INTERVIEWER_ROLE})
    @GetMapping
    public ResponseEntity<List<CandidateResponseDTO>> getAllCandidates() {
        return ResponseEntity.ok(candidateService.findAllOrderedByStatusAndCreatedDate()
                .stream()
                .map(candidateResponseMapper::toCandidateResponseDTO)
                .collect(Collectors.toList()));
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE})
    @PostMapping
    public ResponseEntity<CandidateResponseDTO> createCandidate(@ModelAttribute CandidateRequestDTO candidateRequestDTO) throws IOException {
        Candidate saveCandidate = candidateRequestMapper.toCandidate(candidateRequestDTO);

        if (candidateService.checkExistedEmail(saveCandidate.getEmail())) {
            throw new EmailAlreadyExistException();
        }

        candidateService.save(saveCandidate);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(candidateResponseMapper.toCandidateResponseDTO(saveCandidate));
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE, Constant.INTERVIEWER_ROLE})
    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> getCandidateById(@PathVariable Long id) {
        Optional<Candidate> candidate = candidateService.findById(id);

        return candidate.map(
                c -> ResponseEntity.ok(candidateResponseMapper.toCandidateResponseDTO(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE})
    @PutMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> updateCandidate(@PathVariable Long id,
                                                           @ModelAttribute CandidateRequestDTO candidateRequestDTO) {
        Candidate candidate = candidateService.updateCandidate(id, candidateRequestDTO);
        return ResponseEntity.ok(candidateResponseMapper.toCandidateResponseDTO(candidate));
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE})
    @PatchMapping("/ban/{id}")
    public ResponseEntity<Void> banCandidate(@PathVariable Long id) {
        Candidate candidate = candidateService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        candidate.setCandidateStatus(CandidateStatus.BANNED);
        candidateService.save(candidate);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadCVFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] cv = candidateService.downloadCVFromFileSystem(fileName);
        MediaType mediaType = FileUtil.getMediaTypeForFileName(fileName);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(cv);
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE, Constant.INTERVIEWER_ROLE})
    @GetMapping(params = "status")
    public ResponseEntity<List<ValidCandidateInputDTO>> getCandidatesByStatus(@RequestParam CandidateStatus status) {
        return ResponseEntity.ok(candidateService.getCandidatesByStatus(status));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Candidate>> findAllCandidates
            (@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<Candidate> candidatePage = candidateService.getAllOrderByCandidateStatusAndDate(pageNo);
        if (pageNo > candidatePage.getTotalPages() || pageNo < 1) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(candidatePage);
    }
}

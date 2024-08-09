package be_im_interview_management.service.domainService;


import be_im_interview_management.dto.CandidateRequestDTO;
import be_im_interview_management.dto.ValidCandidateInputDTO;
import be_im_interview_management.entities.Candidate;
import org.springframework.data.domain.Page;
import be_im_interview_management.enums.CandidateStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 3:17 PM
 */
public interface CandidateService extends BaseService<Candidate, Long> {
    List<Candidate> findAllOrderedByStatusAndCreatedDate();
    Candidate updateCandidate(Long id, CandidateRequestDTO candidateRequestDTO);
    String uploadCVToFileSystem(MultipartFile file) throws IOException;
    byte[] downloadCVFromFileSystem(String fileName) throws IOException;
    List<ValidCandidateInputDTO> getCandidatesByStatus(CandidateStatus candidateStatus);
    boolean checkExistedEmail(String email);
    Page<Candidate> getAllOrderByCandidateStatusAndDate(Integer pageNo);
    Optional<Candidate> findByEmail(String email);
}

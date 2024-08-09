package be_im_interview_management.repositories;


import be_im_interview_management.dto.ValidCandidateInputDTO;
import be_im_interview_management.entities.Candidate;
import be_im_interview_management.enums.CandidateStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 3:14 PM
 */
@Repository
public interface CandidateRepository extends BaseRepository<Candidate, Long> {
    @Query("SELECT c FROM Candidate c ORDER BY FUNCTION('FIELD', c.candidateStatus, 'WAITING_FOR_INTERVIEW', 'WAITING_FOR_APPROVAL', 'WAITING_FOR_RESPONSE', 'OPEN', 'PASSED_INTERVIEW', 'APPROVED_OFFER', 'REJECTED_OFFER', 'ACCEPTED_OFFER', 'DECLINED_OFFER', 'CANCELLED_OFFER', 'FAILED_INTERVIEW', 'CANCELLED_INTERVIEW', 'BANNED'), c.createdDate")
    List<Candidate> findAllOrderedByStatusAndCreatedDate();
    Optional<Candidate> findByEmail(String email);

    List<Candidate> findAllByCandidateStatus(CandidateStatus status);

    boolean existsByEmail(String email);

    @Query("SELECT c FROM Candidate c ORDER BY " +
            "CASE WHEN c.candidateStatus = 'WAITING_FOR_INTERVIEW' THEN 1 " +
            "WHEN c.candidateStatus = 'WAITING_FOR_APPROVAL' THEN 2 " +
            "WHEN c.candidateStatus = 'WAITING_FOR_RESPONSE' THEN 3 " +
            "WHEN c.candidateStatus = 'OPEN' THEN 4 " +
            "WHEN c.candidateStatus = 'PASSED_INTERVIEW' THEN 5 " +
            "WHEN c.candidateStatus = 'APPROVED_OFFER' THEN 6 " +
            "WHEN c.candidateStatus = 'REJECTED_OFFER' THEN 7 " +
            "WHEN c.candidateStatus = 'ACCEPTED_OFFER' THEN 8 " +
            "WHEN c.candidateStatus = 'DECLINED_OFFER' THEN 9 " +
            "WHEN c.candidateStatus = 'CANCELLED_OFFER' THEN 10 " +
            "WHEN c.candidateStatus = 'FAILED_INTERVIEW' THEN 11 " +
            "WHEN c.candidateStatus = 'CANCELLED_INTERVIEW' THEN 12 " +
            "WHEN c.candidateStatus = 'BANNED' THEN 13 END, " +
            "c.createdDate ASC ")
    Page<Candidate> getAllOrderByCandidateStatusAndDate(Pageable pageable);
}

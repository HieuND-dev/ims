package be_im_interview_management.repositories;

import be_im_interview_management.entities.InterviewSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 3:15 PM
 */
@Repository
public interface InterviewScheduleRepository extends BaseRepository<InterviewSchedule, Long>{

    Optional<InterviewSchedule> findByScheduleTitle(String scheduleTitle);

    @Query("SELECT i.scheduleTitle FROM InterviewSchedule i JOIN Candidate c ON i.candidate = c WHERE i.result = 'PASSED' AND c.candidateStatus = 'PASSED_INTERVIEW'")
    List<String> findAllValidInterviewTitles();
}

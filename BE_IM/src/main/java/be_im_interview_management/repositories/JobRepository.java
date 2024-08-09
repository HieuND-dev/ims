package be_im_interview_management.repositories;

import be_im_interview_management.entities.Job;
import be_im_interview_management.enums.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 3:16 PM
 */
@Repository
public interface JobRepository extends BaseRepository<Job, Long>{
    List<Job> findByJobStatusAndStartDate(JobStatus status, LocalDate startDate);
    List<Job> findByJobStatusAndEndDate(JobStatus status, LocalDate endDate);

    Optional<Job> findByJobTitle(String jobTitle);

    @Query("SELECT j.jobTitle FROM Job j WHERE j.jobStatus = :jobStatus")
    List<String> findAllJobTitleByStatus(@Param("jobStatus") JobStatus status);

    @Query("SELECT j FROM Job j ORDER BY " +
            "CASE WHEN j.jobStatus = 'OPEN' THEN 1 " +
            "WHEN j.jobStatus = 'DRAFT' THEN 2 " +
            "WHEN j.jobStatus = 'CLOSED' THEN 3 END, " +
            "j.createdDate ASC ")
    Page<Job> getAllOrderByJobStatusAndDate(Pageable pageable);
}

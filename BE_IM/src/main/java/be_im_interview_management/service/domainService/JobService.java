package be_im_interview_management.service.domainService;


import be_im_interview_management.entities.Job;
import be_im_interview_management.enums.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 3:19 PM
 */
public interface JobService extends BaseService<Job, Long> {
    Page<Job> getAllOrderByJobStatusAndDate(Integer pageNo);
    Optional<Job> findByJobTitle(String jobTitle);
    List<String> getJobTitlesByStatus(JobStatus jobStatus);

}

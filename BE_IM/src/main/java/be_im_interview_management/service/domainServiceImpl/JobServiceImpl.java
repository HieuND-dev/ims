package be_im_interview_management.service.domainServiceImpl;


import be_im_interview_management.entities.Job;
import be_im_interview_management.enums.JobStatus;
import be_im_interview_management.repositories.JobRepository;
import be_im_interview_management.service.domainService.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 3:28 PM
 */
@Service
@EnableScheduling
@Slf4j
public class JobServiceImpl extends BaseServiceImpl<Job, Long> implements JobService {
    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        super(jobRepository);
        this.jobRepository = jobRepository;
    }

    @Override
    public Job save(Job entity) {
        LocalDate jobDate = entity.getStartDate();
        LocalDate localDate = LocalDate.now();

        if (localDate.isEqual(jobDate)) {
            entity.setJobStatus(JobStatus.OPEN);
        } else {
            entity.setJobStatus(JobStatus.DRAFT);
        }
        return jobRepository.save(entity);
    }

    @Override
    public Optional<Job> findById(Long id) {
        return jobRepository.findById(id);
    }

    @Scheduled(cron = "0 0 0 ? * *", zone = "Asia/Ho_Chi_Minh")
    @Transactional
    public void updateJobStatus() {
        LocalDate currentDate = LocalDate.now();
        System.out.println("Current Date: " + currentDate);
            List<Job> jobOpen = jobRepository.findByJobStatusAndStartDate(JobStatus.DRAFT, currentDate);
            if(!jobOpen.isEmpty()) {
                for (Job j : jobOpen) {
                    j.setJobStatus(JobStatus.OPEN);
                    log.info("Job ID {} status updated to OPEN", j.getId());
                }
                jobRepository.saveAll(jobOpen);
            }else{
                log.info("Job ID {} not have status update to OPEN !");
            }

            List<Job> jobClosed = jobRepository.findByJobStatusAndEndDate(JobStatus.OPEN, currentDate);
            if(!jobClosed.isEmpty()) {
                for (Job j : jobClosed) {
                    j.setJobStatus(JobStatus.CLOSED);
                    log.info("Job ID {} status updated to CLOSED", j.getId());
                }
                jobRepository.saveAll(jobClosed);
            }else{
                log.info("Job ID {} not have status update to CLOSED !");
            }
            log.info("End !");

    }

    @Override
    public Page<Job> getAllOrderByJobStatusAndDate(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo -1, 10);
        return jobRepository.getAllOrderByJobStatusAndDate(pageable);
    }

    @Override
    public Optional<Job> findByJobTitle(String jobTitle) {
        return jobRepository.findByJobTitle(jobTitle);
    }

    @Override
    public List<String> getJobTitlesByStatus(JobStatus jobStatus) {
        return jobRepository.findAllJobTitleByStatus(jobStatus);
    }

}

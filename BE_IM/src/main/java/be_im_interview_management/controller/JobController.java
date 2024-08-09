package be_im_interview_management.controller;

import be_im_interview_management.dto.JobDTO;
import be_im_interview_management.entities.Job;
import be_im_interview_management.enums.Constant;
import be_im_interview_management.enums.JobStatus;
import be_im_interview_management.service.domainService.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @GetMapping("/jobList")
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<Job> jobs = jobService.findAll(Optional.empty());

        List<JobDTO> dtoList = jobs.stream()
                .sorted(Comparator.comparing((Job job) -> job.getJobStatus().ordinal())
                        .thenComparing(Job::getStartDate, Comparator.reverseOrder()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    private JobDTO convertToDTO(Job job) {
        JobDTO dto = new JobDTO();
        dto.setId(job.getId());
        dto.setJobTitle(job.getJobTitle());
        dto.setStartDate(job.getStartDate());
        dto.setEndDate(job.getEndDate());
        dto.setWorkingAddress(job.getWorkingAddress());
        dto.setSkills(job.getSkills());
        dto.setSalaryFrom(job.getSalaryFrom());
        dto.setSalaryTo(job.getSalaryTo());
        dto.setJobStatus(job.getJobStatus());
        dto.setBenefits(job.getBenefits());
        dto.setLevels(job.getLevels());
        dto.setDescription(job.getDescription());

        return dto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
        Optional<Job> job = jobService.findById(id);

        return job.map(j -> ResponseEntity.ok(convertToDTO(j)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE , Constant.RECRUITER_ROLE})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        if (jobService.findById(id).isPresent()) {
            jobService.deleteById(id);
            return ResponseEntity.ok("Job with ID: "+id+" has been successfully deleted.");
        } else {
            return ResponseEntity.status(404).body("Job with ID " + id + " not found.");
        }
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE , Constant.RECRUITER_ROLE})
    @PostMapping("/add")
    public ResponseEntity<Job> addJob(@RequestBody Job job) {
        jobService.save(job);
        return ResponseEntity.ok(job);
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE , Constant.RECRUITER_ROLE})
    @PatchMapping("/edit/{id}")
    public ResponseEntity<Job> editJob(@PathVariable Long id, @RequestBody Job jobDetails) {
        Optional<Job> findJob = jobService.findById(id);
        if (findJob.isPresent()) {
            Job job = findJob.get();
            job.setJobTitle(jobDetails.getJobTitle());
            job.setStartDate(jobDetails.getStartDate());
            job.setEndDate(jobDetails.getEndDate());
            job.setWorkingAddress(jobDetails.getWorkingAddress());
            job.setSkills(jobDetails.getSkills());
            job.setSalaryFrom(jobDetails.getSalaryFrom());
            job.setSalaryTo(jobDetails.getSalaryTo());
            job.setJobStatus(jobDetails.getJobStatus());
            job.setBenefits(jobDetails.getBenefits());
            job.setLevels(jobDetails.getLevels());
            job.setDescription(jobDetails.getDescription());

            return ResponseEntity.ok(jobService.save(job));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<Job>> getAllJobs
            (@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<Job> jobs = jobService.getAllOrderByJobStatusAndDate(pageNo);
        if (pageNo > jobs.getTotalPages() || pageNo < 1 ) {
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.ok(jobs);
    }

    @GetMapping(params = "status")
    public ResponseEntity<List<String>> getJobsByStatus(@RequestParam JobStatus status) {
        return ResponseEntity.ok(jobService.getJobTitlesByStatus(status));
    }



}
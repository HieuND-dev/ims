package be_im_interview_management.dto.mapper;

import be_im_interview_management.dto.InterviewScheduleRequestDTO;
import be_im_interview_management.entities.Account;
import be_im_interview_management.entities.Candidate;
import be_im_interview_management.entities.InterviewSchedule;
import be_im_interview_management.entities.Job;
import be_im_interview_management.enums.CandidateStatus;
import be_im_interview_management.enums.InterviewResult;
import be_im_interview_management.enums.InterviewStatus;
import be_im_interview_management.repositories.JobRepository;
import be_im_interview_management.service.domainService.AccountService;
import be_im_interview_management.service.domainService.CandidateService;
import be_im_interview_management.service.domainService.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InterviewScheduleRequestMapper {

    private final AccountService accountService;
    private final CandidateService candidateService;
    private final JobService jobService;

    public InterviewSchedule toInterviewSchedule (InterviewScheduleRequestDTO interviewScheduleRequestDTO){
        Account recruiterOwner = accountService.findByUsername(interviewScheduleRequestDTO.recruiterOwner())
                .orElseThrow(() -> new IllegalArgumentException("Invalid recruiter owner name"));

        Job job = jobService.findByJobTitle(interviewScheduleRequestDTO.jobTitle())
                .orElseThrow(() -> new IllegalArgumentException("Invalid job title"));

        Candidate candidate = candidateService.findByEmail(interviewScheduleRequestDTO.candidateEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid candidate name"));

        Account interviewer = accountService.findByUsername(interviewScheduleRequestDTO.interviewerName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid interviewer name"));

        candidate.setCandidateStatus(CandidateStatus.WAITING_FOR_INTERVIEW);
        candidateService.save(candidate);

        return InterviewSchedule.builder()
                .id(interviewScheduleRequestDTO.id())
                .scheduleTitle(interviewScheduleRequestDTO.scheduleTitle())
                .scheduleDate(interviewScheduleRequestDTO.scheduleDate())
                .scheduleFrom(interviewScheduleRequestDTO.scheduleFrom())
                .scheduleTo(interviewScheduleRequestDTO.scheduleTo())
                .status(InterviewStatus.OPEN)
                .location(interviewScheduleRequestDTO.location())
                .meetingId(interviewScheduleRequestDTO.meetingId())
                .job(job)
                .candidate(candidate)
                .interviewer(interviewer)
                .recruiterOwner(recruiterOwner)
                .build();
    }

    public InterviewScheduleRequestDTO toInterviewScheduleRequestDTO(InterviewSchedule interviewSchedule){

        return new InterviewScheduleRequestDTO(
                interviewSchedule.getId(),
                interviewSchedule.getScheduleTitle(),
                interviewSchedule.getScheduleDate(),
                interviewSchedule.getScheduleFrom(),
                interviewSchedule.getScheduleTo(),
                interviewSchedule.getResult(),
                interviewSchedule.getLocation(),
                interviewSchedule.getMeetingId(),
                interviewSchedule.getNote(),
                interviewSchedule.getCandidate().getEmail(),
                interviewSchedule.getJob().getJobTitle(),
                interviewSchedule.getRecruiterOwner().getUsername(),
                interviewSchedule.getInterviewer().getUsername()
        );
    }
}

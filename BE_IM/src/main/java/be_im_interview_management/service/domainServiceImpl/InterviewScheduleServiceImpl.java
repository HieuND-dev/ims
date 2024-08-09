package be_im_interview_management.service.domainServiceImpl;

import be_im_interview_management.dto.InterviewScheduleRequestDTO;
import be_im_interview_management.entities.Account;
import be_im_interview_management.entities.Candidate;
import be_im_interview_management.entities.InterviewSchedule;
import be_im_interview_management.entities.Job;
import be_im_interview_management.enums.CandidateStatus;
import be_im_interview_management.enums.InterviewResult;
import be_im_interview_management.enums.InterviewStatus;
import be_im_interview_management.enums.Role;
import be_im_interview_management.exception.PermissionRequiredException;
import be_im_interview_management.exception.ResourceNotFoundException;
import be_im_interview_management.repositories.InterviewScheduleRepository;
import be_im_interview_management.service.domainService.AccountService;
import be_im_interview_management.service.domainService.CandidateService;
import be_im_interview_management.service.domainService.InterviewScheduleService;
import be_im_interview_management.service.domainService.JobService;
import be_im_interview_management.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 3:26 PM
 */
@Service
public class InterviewScheduleServiceImpl extends BaseServiceImpl<InterviewSchedule, Long>
        implements InterviewScheduleService {

    private final InterviewScheduleRepository interviewScheduleRepository;
    private final JobService jobService;
    private final CandidateService candidateService;
    private final AccountService accountService;
    private final SecurityUtil securityUtil;

    public InterviewScheduleServiceImpl(InterviewScheduleRepository interviewScheduleRepository, JobService jobService, CandidateService candidateService, AccountService accountService, SecurityUtil securityUtil) {
        super(interviewScheduleRepository);
        this.interviewScheduleRepository = interviewScheduleRepository;
        this.jobService = jobService;
        this.candidateService = candidateService;
        this.accountService = accountService;
        this.securityUtil = securityUtil;
    }

    @Override
    public Optional<InterviewSchedule> findByScheduleTitle(String scheduleTitle) {
        return interviewScheduleRepository.findByScheduleTitle(scheduleTitle);
    }


    @Override
    public InterviewSchedule updateInterviewSchedule(Long id, InterviewScheduleRequestDTO interviewScheduleRequestDTO) {
        return interviewScheduleRepository.findById(id)
                .map(interviewSchedule -> {
                    interviewSchedule.setScheduleTitle(interviewScheduleRequestDTO.scheduleTitle());
                    interviewSchedule.setScheduleDate(interviewScheduleRequestDTO.scheduleDate());
                    interviewSchedule.setScheduleFrom(interviewScheduleRequestDTO.scheduleFrom());
                    interviewSchedule.setScheduleTo(interviewScheduleRequestDTO.scheduleTo());
                    interviewSchedule.setResult(interviewScheduleRequestDTO.result());
                    interviewSchedule.setLocation(interviewScheduleRequestDTO.location());
                    interviewSchedule.setMeetingId(interviewScheduleRequestDTO.meetingId());
                    interviewSchedule.setNote(interviewScheduleRequestDTO.note());


                    Job job = jobService.findByJobTitle(interviewScheduleRequestDTO.jobTitle())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid job ID"));
                    interviewSchedule.setJob(job);

                    Account interviewer = accountService.findByUsername(interviewScheduleRequestDTO.interviewerName())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid interviewer ID"));
                    interviewSchedule.setInterviewer(interviewer);

                    Account recruiterOwner = accountService.findByUsername(interviewScheduleRequestDTO.recruiterOwner())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid username"));
                    interviewSchedule.setRecruiterOwner(recruiterOwner);

                    if (InterviewResult.PASSED.equals(interviewScheduleRequestDTO.result())) {
                        interviewSchedule.getCandidate().setCandidateStatus(CandidateStatus.PASSED_INTERVIEW);
                    } else if (InterviewResult.FAILED.equals(interviewScheduleRequestDTO.result())) {
                        interviewSchedule.getCandidate().setCandidateStatus(CandidateStatus.FAILED_INTERVIEW);
                    }

                    interviewSchedule.setStatus(InterviewStatus.INTERVIEWED);
                    candidateService.save(interviewSchedule.getCandidate());

                    return interviewScheduleRepository.save(interviewSchedule);
                }).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public InterviewSchedule submitInterviewResult(Long id, InterviewResult result, String note) {
        InterviewSchedule interview = interviewScheduleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id));

        String interviewer = interview.getInterviewer().getUsername();
        String requestUsername = securityUtil.getCurrentUser().getUsername();
        Role requestRole = securityUtil.getCurrentUser().getRole();

        if (requestRole == Role.INTERVIEWER && (!interviewer.equals(requestUsername))) {
            throw new PermissionRequiredException();
        }

        return interviewScheduleRepository.findById(id)
                .map(interviewSchedule -> {
                    interviewSchedule.setResult(result);
                    interviewSchedule.setNote(note);

                    Candidate candidate = interviewSchedule.getCandidate();

                    if (InterviewResult.PASSED.equals(result)){
                        candidate.setCandidateStatus(CandidateStatus.PASSED_INTERVIEW);
                    } else if (InterviewResult.FAILED.equals(result)){
                        candidate.setCandidateStatus(CandidateStatus.FAILED_INTERVIEW);
                    }

                    interviewSchedule.setStatus(InterviewStatus.INTERVIEWED);
                    candidateService.save(candidate);

                    return interviewScheduleRepository.save(interviewSchedule);
                }).orElseThrow(() -> new ResourceNotFoundException(id));
    }


    @Override
    @Transactional
    public boolean cancelInterviewSchedule(Long id) {
        Optional<InterviewSchedule> interviewScheduleOpt = interviewScheduleRepository.findById(id);
        if (interviewScheduleOpt.isPresent()) {
            InterviewSchedule interviewSchedule = interviewScheduleOpt.get();
            interviewSchedule.setStatus(InterviewStatus.valueOf(InterviewStatus.CANCELLED.name()));

            interviewScheduleRepository.save(interviewSchedule);
            return true;
        }
        return false;
    }

    @Override
    public Page<InterviewSchedule> getAllInterviewSchedules(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo -1, 6);
        return interviewScheduleRepository.findAll(pageable);
    }

    @Override
    public List<String> getAllValidInterviewTitles() {
        return interviewScheduleRepository.findAllValidInterviewTitles();
    }
}

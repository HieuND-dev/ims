package be_im_interview_management.service.domainService;


import be_im_interview_management.dto.InterviewScheduleRequestDTO;
import be_im_interview_management.entities.InterviewSchedule;
import be_im_interview_management.enums.InterviewResult;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 3:19 PM
 */
public interface InterviewScheduleService extends BaseService<InterviewSchedule, Long> {
    InterviewSchedule updateInterviewSchedule(Long id, InterviewScheduleRequestDTO interviewScheduleRequestDTO);
    InterviewSchedule submitInterviewResult(Long id, InterviewResult result, String note);
    Optional<InterviewSchedule> findByScheduleTitle(String scheduleTitle);

    boolean cancelInterviewSchedule(Long id);

    Page<InterviewSchedule> getAllInterviewSchedules(Integer pageNo);

    List<String> getAllValidInterviewTitles();
}

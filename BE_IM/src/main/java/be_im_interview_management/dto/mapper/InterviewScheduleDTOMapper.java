package be_im_interview_management.dto.mapper;

import be_im_interview_management.dto.InterviewScheduleDTO;
import be_im_interview_management.entities.InterviewSchedule;
import be_im_interview_management.enums.InterviewResult;
import be_im_interview_management.enums.Role;

import java.time.format.DateTimeFormatter;

public class InterviewScheduleDTOMapper {

    public static InterviewScheduleDTO toDTO(InterviewSchedule interviewSchedule) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String scheduleDateString = interviewSchedule.getScheduleDate().format(dateFormatter);
        String scheduleFromString = interviewSchedule.getScheduleFrom().format(timeFormatter);
        String scheduleToString = interviewSchedule.getScheduleTo().format(timeFormatter);

        String scheduleTime = String.format("%s %s-%s", scheduleDateString, scheduleFromString, scheduleToString);

        String candidateName = (interviewSchedule.getCandidate() != null) ? interviewSchedule.getCandidate().getFullName() : "null";


        String interviewerName = (interviewSchedule.getInterviewer() != null && interviewSchedule.getInterviewer().getRole() == Role.INTERVIEWER) ? interviewSchedule.getInterviewer().getUsername() : "null";

        InterviewResult interviewResult = interviewSchedule.getResult();
        String result = (interviewResult != InterviewResult.PASSED && interviewResult != InterviewResult.FAILED) ? null : interviewResult.name();


        String recruiterName = (interviewSchedule.getRecruiterOwner() != null && interviewSchedule.getRecruiterOwner().getRole() == Role.RECRUITER) ? interviewSchedule.getRecruiterOwner().getUsername() : "null";

        return new InterviewScheduleDTO(
                interviewSchedule.getId(),
                interviewSchedule.getScheduleTitle(),
                candidateName,
                interviewerName,
                scheduleTime,
                result,
                interviewSchedule.getStatus().name(),
                interviewSchedule.getJob().getJobTitle(),
                interviewSchedule.getLocation(),
                interviewSchedule.getMeetingId(),
                interviewSchedule.getNote(),
                recruiterName
        );
    }
}

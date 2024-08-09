package be_im_interview_management.dto;

import be_im_interview_management.enums.InterviewResult;
import be_im_interview_management.enums.InterviewStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record InterviewScheduleRequestDTO(
        Long id,
        String scheduleTitle,
        LocalDate scheduleDate,
        LocalTime scheduleFrom,
        LocalTime scheduleTo,
        InterviewResult result,
        String location,
        String meetingId,
        String note,
        String candidateEmail,
        String jobTitle,
        String recruiterOwner,
        String interviewerName
) {
}

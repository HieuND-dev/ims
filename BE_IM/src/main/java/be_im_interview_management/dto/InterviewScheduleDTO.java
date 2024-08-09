package be_im_interview_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterviewScheduleDTO {
    private Long id;
    private String scheduleTitle;
    private String candidateName;
    private String interviewerName;
    private String scheduleTime;
    private String result;
    private String status;
    private String jobTitle;
    private String location;
    private String meetingId;
    private String note;
    private String recruiterName;
}

package be_im_interview_management.entities;


import be_im_interview_management.enums.InterviewResult;
import be_im_interview_management.enums.InterviewStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by: HieuND64
 * Date Time: 7/24/2024 11:22 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "interview_schedule")
public class InterviewSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("schedule_title")
	@Column(name = "schedule_title", nullable = false, unique = true)
	private String scheduleTitle;

	@JsonProperty("schedule_date")
	@Column(name = "schedule_date", nullable = false)
	private LocalDate scheduleDate;

	@JsonProperty("schedule_from")
	@Column(name = "schedule_from", nullable = false)
	private LocalTime scheduleFrom;

	@JsonProperty("schedule_to")
	@Column(name = "schedule_to", nullable = false)
	private LocalTime scheduleTo;

	@Column(name = "result")
	@Enumerated(EnumType.STRING)
	private InterviewResult result;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private InterviewStatus status;

	@Column(name = "location")
	private String location;

	@JsonProperty("meeting_id")
	@Column(name = "meeting_id", nullable = false)
	private String meetingId;

	@Column(name = "note")
	private String note;

	@OneToOne
	@JoinColumn(name = "candidate_id")
	@JsonProperty("candidate_id")
	private Candidate candidate;

	@ManyToOne
	@JoinColumn(name = "job_id")
	@JsonProperty("job_id")
	private Job job;

	@ManyToOne
	@JoinColumn(name = "interviewer_id")
    @JsonProperty("interviewer_id")
	private Account interviewer;

	@ManyToOne
	@JoinColumn(name = "recruiter_owner_id")
    @JsonProperty("recruiter_owner_id")
	private Account recruiterOwner;

	@OneToOne(mappedBy = "interviewSchedule", cascade = CascadeType.ALL)
	private Offer offer;


}

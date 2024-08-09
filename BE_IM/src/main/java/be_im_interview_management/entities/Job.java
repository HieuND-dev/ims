package be_im_interview_management.entities;


import be_im_interview_management.enums.Benefit;
import be_im_interview_management.enums.JobStatus;
import be_im_interview_management.enums.Level;
import be_im_interview_management.enums.Skill;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: HieuND64
 * Date Time: 7/24/2024 11:21 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job")
public class Job extends AbstractAuditor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("job_title")
    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @JsonProperty("start_date")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @JsonProperty("end_date")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @JsonProperty("working_address")
    @Column(name = "working_address", nullable = false)
    private String workingAddress;

    @ElementCollection(targetClass = Skill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "job_skills", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "skills", nullable = false)
    private List<Skill> skills;

    @JsonProperty("salary_from")
    @Column(name = "salary_from")
    private Double salaryFrom;

    @JsonProperty("salary_to")
    @Column(name = "salary_to", nullable = false)
    private Double salaryTo;

    @JsonProperty("job_status")
    @Column(name = "job_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;

    @ElementCollection(targetClass = Benefit.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "job_benefits", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "benefits", nullable = false)
    private List<Benefit> benefits;

    @ElementCollection(targetClass = Level.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "job_levels", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "levels", nullable = false)
    private List<Level> levels;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewSchedule> interviewSchedules;
}

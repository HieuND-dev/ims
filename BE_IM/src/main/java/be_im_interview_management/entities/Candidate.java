package be_im_interview_management.entities;


import be_im_interview_management.enums.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: HieuND64
 * Date Time: 7/24/2024 11:16 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "candidate")
public class Candidate extends AbstractAuditor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("full_name")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @JsonProperty("date_of_birth")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("phone_no")
    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "address")
    private String address;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "cv_file_name", nullable = false)
    private String cvFileName;

    @Column(name = "position", nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    @ElementCollection(targetClass = Skill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "candidate_skills", joinColumns = @JoinColumn(name = "candidate_id"))
    @Column(name = "skills")
    private List<Skill> skills;

    @Column(name = "note")
    private String note;

    @JsonProperty("candidate_status")
    @Column(name = "candidate_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CandidateStatus candidateStatus;

    @JsonProperty("year_of_experience")
    @Column(name = "year_of_experience", nullable = false)
    private Integer yearOfExperience;

    @JsonProperty("highest_level")
    @Column(name = "highest_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private HighestLevel highestLevel;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    @JsonProperty("recruiter_id")
    private Account recruiter;

    @OneToOne(mappedBy = "candidate", cascade = CascadeType.ALL)
    private InterviewSchedule interviewSchedule;
}

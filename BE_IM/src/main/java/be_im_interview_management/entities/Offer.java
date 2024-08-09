package be_im_interview_management.entities;


import be_im_interview_management.enums.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Created by: HieuND64
 * Date Time: 7/24/2024 12:58 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Offer")
public class Offer extends AbstractAuditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position", nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @JsonProperty("contract_type")
    @Column(name = "contract_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Column(name = "level", nullable = false)
    @Enumerated(EnumType.STRING)
    private Level level;

    @JsonProperty("contract_from")
    @Column(name = "contract_from", nullable = false)
    private LocalDate contractFrom;

    @JsonProperty("contract_to")
    @Column(name = "contract_to")
    private LocalDate contractTo;

    @Column(name = "department", nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;

    @JsonProperty("due_date")
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @JsonProperty("basic_salary")
    @Column(name = "basic_salary", nullable = false)
    private Double basicSalary;

    @Column(name = "note")
    private String note;

    @OneToOne
    @JoinColumn(name = "interview_schedule_id")
    @JsonProperty("interview_schedule_id")
    private InterviewSchedule interviewSchedule;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    @JsonProperty("approver_id")
    private Account approver;

    @ManyToOne
    @JoinColumn(name = "recruiter_owner_id")
    @JsonProperty("recruiter_owner_id")
    private Account recruiterOwner;
}

package be_im_interview_management.entities;


import be_im_interview_management.enums.AccountStatus;
import be_im_interview_management.enums.Department;
import be_im_interview_management.enums.Gender;
import be_im_interview_management.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: HieuND64
 * Date Time: 7/23/2024 9:33 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"tokens"})
@Builder
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @JsonProperty("phone_no")
    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonProperty("account_status")
    @Column(name = "account_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @JsonProperty("full_name")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @JsonProperty("date_of_birth")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "department", nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "account")
    private List<Token> tokens;


    public boolean isActive() {
        return this.accountStatus == AccountStatus.ACTIVE;
    }

}

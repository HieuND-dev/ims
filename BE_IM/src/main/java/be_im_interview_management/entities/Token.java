package be_im_interview_management.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Created by: HieuND64
 * Date Time: 7/27/2024 8:46 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "is_logged_out")
    private boolean isLoggedOut;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}

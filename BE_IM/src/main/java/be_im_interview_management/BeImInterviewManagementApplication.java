package be_im_interview_management;

import be_im_interview_management.entities.Account;
import be_im_interview_management.enums.AccountStatus;
import be_im_interview_management.enums.Department;
import be_im_interview_management.enums.Gender;
import be_im_interview_management.enums.Role;
import be_im_interview_management.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@RequiredArgsConstructor
public class BeImInterviewManagementApplication implements CommandLineRunner {

    private final AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(BeImInterviewManagementApplication.class, args);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        if(accountRepository.findByEmail("admin@gmail.com").isEmpty()) {
            accountRepository.save(Account.builder()
                            .username("MaiDX1")
                            .email("admin@gmail.com")
                            .password(passwordEncoder().encode("12345678"))
                            .phoneNo("0393326903")
                            .role(Role.ADMIN)
                            .accountStatus(AccountStatus.ACTIVE)
                            .fullName("Dao Xuan Mai")
                            .dateOfBirth(LocalDate.of(2003, 01, 01))
                            .address("Hai Duong")
                            .gender(Gender.OTHER)
                            .department(Department.IT)
                            .note("admin account")
                            .build());
        }
        if(accountRepository.findByEmail("manager1@gmail.com").isEmpty()) {
            accountRepository.save(Account.builder()
                    .username("HieuND1")
                    .email("manager1@gmail.com")
                    .password(passwordEncoder().encode("12345678"))
                    .phoneNo("0393326903")
                    .role(Role.MANAGER)
                    .accountStatus(AccountStatus.ACTIVE)
                    .fullName("Nguyen Dang Hieu")
                    .dateOfBirth(LocalDate.of(2003, 01, 01))
                    .address("Hai Duong")
                    .gender(Gender.OTHER)
                    .department(Department.IT)
                    .note("manager account")
                    .build());
        }
        if(accountRepository.findByEmail("manager2@gmail.com").isEmpty()) {
            accountRepository.save(Account.builder()
                    .username("HieuND2")
                    .email("manager2@gmail.com")
                    .password(passwordEncoder().encode("12345678"))
                    .phoneNo("0393326903")
                    .role(Role.MANAGER)
                    .accountStatus(AccountStatus.ACTIVE)
                    .fullName("Nguyen Dang Hieu")
                    .dateOfBirth(LocalDate.of(2003, 01, 01))
                    .address("Hai Duong")
                    .gender(Gender.OTHER)
                    .department(Department.IT)
                    .note("manager account")
                    .build());
        }
        if(accountRepository.findByEmail("recruiter1@gmail.com").isEmpty()) {
            accountRepository.save(Account.builder()
                    .username("AnhPM1")
                    .email("recruiter1@gmail.com")
                    .password(passwordEncoder().encode("12345678"))
                    .phoneNo("0393326903")
                    .role(Role.RECRUITER)
                    .accountStatus(AccountStatus.ACTIVE)
                    .fullName("Phung Minh Anh")
                    .dateOfBirth(LocalDate.of(2003, 01, 01))
                    .address("Hai Duong")
                    .gender(Gender.OTHER)
                    .department(Department.IT)
                    .note("recruiter account")
                    .build());
        }
        if(accountRepository.findByEmail("recruiter2@gmail.com").isEmpty()) {
            accountRepository.save(Account.builder()
                    .username("AnhPM2")
                    .email("recruiter2@gmail.com")
                    .password(passwordEncoder().encode("12345678"))
                    .phoneNo("0393326903")
                    .role(Role.RECRUITER)
                    .accountStatus(AccountStatus.ACTIVE)
                    .fullName("Phung Minh Anh")
                    .dateOfBirth(LocalDate.of(2003, 01, 01))
                    .address("Hai Duong")
                    .gender(Gender.OTHER)
                    .department(Department.IT)
                    .note("recruiter account")
                    .build());
        }
        if(accountRepository.findByEmail("interviewer1@gmail.com").isEmpty()) {
            accountRepository.save(Account.builder()
                    .username("DatNT1")
                    .email("interviewer1@gmail.com")
                    .password(passwordEncoder().encode("12345678"))
                    .phoneNo("0393326903")
                    .role(Role.INTERVIEWER)
                    .accountStatus(AccountStatus.ACTIVE)
                    .fullName("Nguyen Tuan Dat")
                    .dateOfBirth(LocalDate.of(2003, 01, 01))
                    .address("Hai Duong")
                    .gender(Gender.OTHER)
                    .department(Department.IT)
                    .note("interviewer account")
                    .build());
        }
        if(accountRepository.findByEmail("interviewer2@gmail.com").isEmpty()) {
            accountRepository.save(Account.builder()
                    .username("DatNT2")
                    .email("interviewer2@gmail.com")
                    .password(passwordEncoder().encode("12345678"))
                    .phoneNo("0393326903")
                    .role(Role.INTERVIEWER)
                    .accountStatus(AccountStatus.ACTIVE)
                    .fullName("Nguyen Tuan Dat")
                    .dateOfBirth(LocalDate.of(2003, 01, 01))
                    .address("Hai Duong")
                    .gender(Gender.OTHER)
                    .department(Department.IT)
                    .note("interviewer account")
                    .build());
        }
    }
}

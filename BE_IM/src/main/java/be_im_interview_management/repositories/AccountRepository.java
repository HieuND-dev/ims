package be_im_interview_management.repositories;

import be_im_interview_management.entities.Account;
import be_im_interview_management.enums.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 9:37 AM
 */
@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {

    Optional<Account> findByEmail(String email);
    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT a.username FROM Account a WHERE a.role = :role ORDER BY a.username")
    List<String> findUsernamesByRole(@Param("role") Role role);
}

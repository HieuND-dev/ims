package be_im_interview_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 9:36 AM
 */
@NoRepositoryBean
public interface BaseRepository<T, ID>  extends JpaRepository<T, ID> {
}

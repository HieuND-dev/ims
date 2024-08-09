package be_im_interview_management.service.domainService;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 9:39 AM
 */
public interface BaseService<T, ID> {

    T save(T entity);

    List<T> findAll(Optional<Sort> sort);

    Optional<T> findById(ID id);

    void deleteById(ID id);
}

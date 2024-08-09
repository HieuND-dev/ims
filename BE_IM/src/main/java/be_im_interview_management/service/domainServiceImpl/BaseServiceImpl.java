package be_im_interview_management.service.domainServiceImpl;



import be_im_interview_management.repositories.BaseRepository;
import be_im_interview_management.service.domainService.BaseService;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 9:41 AM
 */
public class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

    private final BaseRepository<T, ID> baseRepository;

    public BaseServiceImpl(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public T save(T entity) {
        return baseRepository.save(entity);
    }

    @Override
    public List<T> findAll(Optional<Sort> sort) {
        return baseRepository.findAll();
    }

    @Override
    public Optional<T> findById(ID id) {
        return baseRepository.findById(id);
    }

    @Override
    public void deleteById(ID id) {
        baseRepository.deleteById(id);
    }
}

package be_im_interview_management.repositories;


import be_im_interview_management.entities.Offer;
import be_im_interview_management.enums.OfferStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 3:17 PM
 */
@Repository
public interface OfferRepository extends BaseRepository<Offer, Long>{
    List<Offer> findByStatusAndDueDateBefore(OfferStatus status, LocalDate date);
}

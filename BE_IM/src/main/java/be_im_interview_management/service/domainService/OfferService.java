package be_im_interview_management.service.domainService;


import be_im_interview_management.dto.OfferRequestDTO;
import be_im_interview_management.entities.Offer;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 3:20 PM
 */
public interface OfferService extends BaseService<Offer, Long> {
    Offer updateOffer(Long id, OfferRequestDTO offerRequestDTO);

    String updateOfferStatus(Long id, String action);

    ByteArrayInputStream getOffersDownloaded() throws IOException;

    public List<Offer> getOffersWaitingForResponseDueToday();

    void sendReminder() throws MessagingException;

    Page<Offer> getAllOffers(Integer pageNo);
}

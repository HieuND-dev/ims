package be_im_interview_management.service.domainServiceImpl;


import be_im_interview_management.dto.OfferRequestDTO;
import be_im_interview_management.dto.mapper.OfferRequestMapper;
import be_im_interview_management.entities.Account;
import be_im_interview_management.entities.Candidate;
import be_im_interview_management.entities.Offer;
import be_im_interview_management.enums.CandidateStatus;
import be_im_interview_management.enums.OfferStatus;
import be_im_interview_management.enums.Role;
import be_im_interview_management.exception.ActionNotFoundException;
import be_im_interview_management.exception.PermissionRequiredException;
import be_im_interview_management.exception.ResourceNotFoundException;
import be_im_interview_management.repositories.AccountRepository;
import be_im_interview_management.repositories.CandidateRepository;
import be_im_interview_management.repositories.OfferRepository;
import be_im_interview_management.service.EmailService;
import be_im_interview_management.service.domainService.OfferService;
import be_im_interview_management.util.ExcelUtil;
import be_im_interview_management.util.SecurityUtil;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 3:29 PM
 */
@Service
@EnableScheduling
public class OfferServiceImpl extends BaseServiceImpl<Offer, Long> implements OfferService {

    private final OfferRepository offerRepository;
    private final AccountRepository accountRepository;
    private final CandidateRepository candidateRepository;
    private final OfferRequestMapper offerRequestMapper;
    private final EmailService emailService;
    private final SecurityUtil securityUtil;

    public OfferServiceImpl(OfferRepository offerRepository, AccountRepository accountRepository, CandidateRepository candidateRepository, OfferRequestMapper offerRequestMapper, EmailService emailService, SecurityUtil securityUtil) {
        super(offerRepository);
        this.offerRepository = offerRepository;
        this.accountRepository = accountRepository;
        this.candidateRepository = candidateRepository;
        this.offerRequestMapper = offerRequestMapper;
        this.emailService = emailService;
        this.securityUtil = securityUtil;
    }

    @Override
    public Offer updateOffer(Long id, OfferRequestDTO offerRequestDTO) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        String offerOwnerHR = offer.getRecruiterOwner().getUsername();
        String offerApproval = offer.getApprover().getUsername();
        String requestUsername = securityUtil.getCurrentUser().getUsername();
        Role requestRole = securityUtil.getCurrentUser().getRole();

        if ((requestRole == Role.RECRUITER && (!offerOwnerHR.equals(requestUsername)))
                || (requestRole == Role.MANAGER && (!offerApproval.equals(requestUsername)))) {
            throw new PermissionRequiredException();
        }

        Account approver = accountRepository.findByUsername(offerRequestDTO.approver())
                .orElseThrow(() -> new IllegalArgumentException("Invalid approver username"));
        Account recruiterOwner = accountRepository.findByUsername(offerRequestDTO.recruiterOwner())
                .orElseThrow(() -> new IllegalArgumentException("Invalid recruiterOwner username"));

        return offerRepository.findById(id)
                .map(o -> {
                    OfferStatus offerStatus =offerRequestDTO.status();

                    Candidate candidate = o.getInterviewSchedule().getCandidate();
                    if (offerStatus == OfferStatus.WAITING_FOR_RESPONSE) {
                        candidate.setCandidateStatus(CandidateStatus.WAITING_FOR_RESPONSE);
                        candidateRepository.save(candidate);
                    } else if (offerStatus == OfferStatus.WAITING_FOR_APPROVAL) {
                        candidate.setCandidateStatus(CandidateStatus.WAITING_FOR_APPROVAL);
                        candidateRepository.save(candidate);
                    } else {
                        throw new ActionNotFoundException("Update not allowed status");
                    }

                    o.setStatus(offerStatus);
                    o.setPosition(offerRequestDTO.position());
                    o.setApprover(approver);
                    o.setContractFrom(offerRequestDTO.contractFrom());
                    o.setContractTo(offerRequestDTO.contractTo());
                    o.setContractType(offerRequestDTO.contractType());
                    o.setLevel(offerRequestDTO.level());
                    o.setDepartment(offerRequestDTO.department());
                    o.setRecruiterOwner(recruiterOwner);
                    o.setDueDate(offerRequestDTO.dueDate());
                    o.setBasicSalary(offerRequestDTO.basicSalary());
                    o.setNote(offerRequestDTO.offerNote());
                    return offerRepository.save(o);
                }).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public String updateOfferStatus(Long id, String action) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        OfferStatus offerStatus;
        CandidateStatus candidateStatus;
        String message;

        String offerOwnerHR = offer.getRecruiterOwner().getUsername();
        String offerApproval = offer.getApprover().getUsername();
        String requestUsername = securityUtil.getCurrentUser().getUsername();
        Role requestRole = securityUtil.getCurrentUser().getRole();

        switch (action) {
            case "approve":
                if (requestRole == Role.RECRUITER
                        || (requestRole == Role.MANAGER && (!offerApproval.equals(requestUsername)))) {
                    throw new PermissionRequiredException();
                }
                offerStatus = OfferStatus.APPROVED_OFFER;
                candidateStatus = CandidateStatus.APPROVED_OFFER;
                message = "Offer has been approved successfully";
                break;
            case "reject":
                if (requestRole == Role.RECRUITER
                        || (requestRole == Role.MANAGER && (!offerApproval.equals(requestUsername)))) {
                    throw new PermissionRequiredException();
                }
                offerStatus = OfferStatus.REJECTED_OFFER;
                candidateStatus = CandidateStatus.REJECTED_OFFER;
                message = "Offer has been rejected";
                break;
            case "sent":
                offerStatus = OfferStatus.WAITING_FOR_RESPONSE;
                candidateStatus = CandidateStatus.WAITING_FOR_RESPONSE;
                message = "Offer has been sent to candidate";
                break;
            case "accept":
                if ((requestRole == Role.RECRUITER && (!offerOwnerHR.equals(requestUsername)))
                        || (requestRole == Role.MANAGER && (!offerApproval.equals(requestUsername)))) {
                    throw new PermissionRequiredException();
                }
                offerStatus = OfferStatus.ACCEPTED_OFFER;
                candidateStatus = CandidateStatus.ACCEPTED_OFFER;
                message = "Offer has been accepted";
                break;
            case "decline":
                if ((requestRole == Role.RECRUITER && (!offerOwnerHR.equals(requestUsername)))
                        || (requestRole == Role.MANAGER && (!offerApproval.equals(requestUsername)))) {
                    throw new PermissionRequiredException();
                }
                offerStatus = OfferStatus.DECLINED_OFFER;
                candidateStatus = CandidateStatus.DECLINED_OFFER;
                message = "Offer has been declined";
                break;
            case "cancel":
                if ((requestRole == Role.RECRUITER && (!offerOwnerHR.equals(requestUsername)))
                || (requestRole == Role.MANAGER && (!offerApproval.equals(requestUsername)))) {
                    throw new PermissionRequiredException();
                }
                offerStatus = OfferStatus.CANCELLED_OFFER;
                candidateStatus = CandidateStatus.CANCELLED_OFFER;
                message = "Offer has been cancelled";
                break;
            default:
                throw new ActionNotFoundException(action);
        }

        offer.setStatus(offerStatus);
        offerRepository.save(offer);

        Candidate candidate = offer.getInterviewSchedule().getCandidate();
        candidate.setCandidateStatus(candidateStatus);
        candidateRepository.save(candidate);

        return message;
    }

    @Override
    public ByteArrayInputStream getOffersDownloaded() throws IOException {
        List<OfferRequestDTO> offersList = offerRepository
                .findAll()
                .stream()
                .map(offerRequestMapper::toOfferRequestDTO)
                .toList();

        return ExcelUtil.dataToExcel(offersList);
    }

    @Override
    public List<Offer> getOffersWaitingForResponseDueToday() {
        return offerRepository.findByStatusAndDueDateBefore(OfferStatus.WAITING_FOR_APPROVAL, LocalDate.now().plusDays(1));
    }

    @Override
    @Scheduled(cron = "0 0 8 ? * *", zone = "Asia/Ho_Chi_Minh")
    public void sendReminder() throws MessagingException {

        List<Offer> offers = getOffersWaitingForResponseDueToday();

        if (!offers.isEmpty()) {
            for (Offer offer: offers) {
                String email = offer.getApprover().getEmail();
                emailService.sendOfferReminderEmail(email, offer);
            }
        }
    }

    @Override
    public Page<Offer> getAllOffers(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo -1 ,6);
        return offerRepository.findAll(pageable);
    }

}

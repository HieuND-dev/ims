package be_im_interview_management.controller;

import be_im_interview_management.dto.OfferRequestDTO;
import be_im_interview_management.dto.mapper.OfferRequestMapper;
import be_im_interview_management.entities.Candidate;
import be_im_interview_management.entities.Offer;
import be_im_interview_management.enums.CandidateStatus;
import be_im_interview_management.enums.Constant;
import be_im_interview_management.enums.OfferStatus;
import be_im_interview_management.service.domainService.CandidateService;
import be_im_interview_management.service.domainService.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by: HieuND64
 * Date Time: 7/30/2024 10:34 AM
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;
    private final OfferRequestMapper offerRequestMapper;
    private final CandidateService candidateService;

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE})
    @GetMapping
    public ResponseEntity<List<OfferRequestDTO>> getOffers() {
        return ResponseEntity.ok(offerService.findAll(Optional.empty())
                .stream().map(offerRequestMapper::toOfferRequestDTO)
                .collect(Collectors.toList()));
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE})
    @PostMapping
    public ResponseEntity<String> createOffer(@RequestBody OfferRequestDTO offerRequestDTO) {
        Offer offer = offerRequestMapper.toOffer(offerRequestDTO);
        offer.setStatus(OfferStatus.WAITING_FOR_APPROVAL);
        offerService.save(offer);

        Candidate candidate = offer.getInterviewSchedule().getCandidate();
        candidate.setCandidateStatus(CandidateStatus.WAITING_FOR_APPROVAL);
        candidateService.save(candidate);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Create offer successfully");
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE})
    @GetMapping("/{id}")
    public ResponseEntity<OfferRequestDTO> getOfferById(@PathVariable Long id) {
        Optional<Offer> offer = offerService.findById(id);

        return offer.map(
                o -> ResponseEntity.ok(offerRequestMapper.toOfferRequestDTO(o)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE})
    @PutMapping("/{id}")
    public ResponseEntity<OfferRequestDTO> updateOffer(@PathVariable Long id, @RequestBody OfferRequestDTO offerRequestDTO) {
        Offer offer = offerService.updateOffer(id, offerRequestDTO);
        return ResponseEntity.ok(offerRequestMapper.toOfferRequestDTO(offer));
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE})
    @PatchMapping("/{action}/{id}")
    public ResponseEntity<String> updateOfferStatus(@PathVariable Long id, @PathVariable String action) {
        return ResponseEntity.ok(offerService.updateOfferStatus(id, action));
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE, Constant.RECRUITER_ROLE})
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download() throws IOException {
        String fileName = "offersList.xlsx";
        ByteArrayInputStream inputStream = offerService.getOffersDownloaded();
        InputStreamResource response = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(response);
    }


    @GetMapping("/all")
    public ResponseEntity<Page<Offer>> getAllOffers
            (@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<Offer> offerPage = offerService.getAllOffers(pageNo);
        if (pageNo > offerPage.getTotalPages() || pageNo < 1) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(offerPage);
    }
}

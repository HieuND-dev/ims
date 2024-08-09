package be_im_interview_management.controller;

import be_im_interview_management.dto.InterviewScheduleRequestDTO;
import be_im_interview_management.dto.mapper.InterviewScheduleDTOMapper;
import be_im_interview_management.dto.mapper.InterviewScheduleRequestMapper;

import be_im_interview_management.dto.InterviewScheduleDTO;
import be_im_interview_management.entities.InterviewSchedule;
import be_im_interview_management.enums.Constant;
import be_im_interview_management.exception.ResourceNotFoundException;
import be_im_interview_management.service.domainService.InterviewScheduleService;
import be_im_interview_management.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/interview-schedule")
public class InterviewScheduleController {

    private final InterviewScheduleService interviewScheduleService;
    private final InterviewScheduleRequestMapper interviewScheduleRequestMapper;
    private final SecurityUtil securityUtil;

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE , Constant.RECRUITER_ROLE})
    @PostMapping("/create")
    public ResponseEntity<String> createInterviewSchedule(@RequestBody InterviewScheduleRequestDTO interviewScheduleRequestDTO) {
        InterviewSchedule savedInterviewSchedule = interviewScheduleRequestMapper
                .toInterviewSchedule(interviewScheduleRequestDTO);
        interviewScheduleService.save(savedInterviewSchedule);
        return ResponseEntity.status(HttpStatus.CREATED).body("Interview schedule created successfully !");
    }

    @Secured({Constant.ADMIN_ROLE, Constant.MANAGER_ROLE , Constant.RECRUITER_ROLE})
    @PutMapping("/update/{id}")
    public ResponseEntity<InterviewScheduleRequestDTO> updateInterviewSchedule(@PathVariable Long id,
                                                       @RequestBody InterviewScheduleRequestDTO interviewScheduleRequestDTO) {
        InterviewSchedule interviewSchedule = interviewScheduleService.updateInterviewSchedule
                (id, interviewScheduleRequestDTO);
        return ResponseEntity.ok(interviewScheduleRequestMapper.toInterviewScheduleRequestDTO(interviewSchedule));
}

    @Secured({Constant.ADMIN_ROLE, Constant.INTERVIEWER_ROLE})
    @PostMapping("/{id}/submitResult")
    public ResponseEntity<InterviewScheduleRequestDTO> submitInterviewResult(@PathVariable Long id,
                                                       @RequestBody InterviewScheduleRequestDTO interviewScheduleRequestDTO) {
        InterviewSchedule updatedInterviewSchedule = interviewScheduleService.submitInterviewResult
                (id, interviewScheduleRequestDTO.result(), interviewScheduleRequestDTO.note());
        return ResponseEntity.ok(interviewScheduleRequestMapper.toInterviewScheduleRequestDTO(updatedInterviewSchedule));


}
    @GetMapping("/schedulesList")
    public ResponseEntity<List<InterviewScheduleDTO>> getAllInterviewSchedules() {
        List<InterviewSchedule> interviewSchedules = interviewScheduleService.findAll(Optional.empty());
        List<InterviewScheduleDTO> dtoList = interviewSchedules.stream()
                .map(InterviewScheduleDTOMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<InterviewScheduleDTO> getInterviewScheduleById(@PathVariable("id") Long id) {
        Optional<InterviewSchedule> interviewScheduleOptional = interviewScheduleService.findById(id);
        if (interviewScheduleOptional.isPresent()) {
            InterviewScheduleDTO dto = InterviewScheduleDTOMapper.toDTO(interviewScheduleOptional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Secured({Constant.ADMIN_ROLE, Constant.RECRUITER_ROLE, Constant.MANAGER_ROLE})
    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelInterviewSchedule(@PathVariable Long id) {
        boolean isCancelled = interviewScheduleService.cancelInterviewSchedule(id);
        if (isCancelled) {
            return ResponseEntity.ok("Interview schedule cancelled successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Interview schedule not found or already cancelled.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity <Page<InterviewSchedule>> getAllInterviewSchedule
            (@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<InterviewSchedule> interviewSchedulePage = interviewScheduleService.getAllInterviewSchedules(pageNo);
        if (pageNo > interviewSchedulePage.getTotalPages() || pageNo < 1) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(interviewSchedulePage);
    }

    @GetMapping("/validInterviews")
    public ResponseEntity<List<String>> getAllValidInterviewTitles() {
        return ResponseEntity.ok(interviewScheduleService.getAllValidInterviewTitles());
    }

}

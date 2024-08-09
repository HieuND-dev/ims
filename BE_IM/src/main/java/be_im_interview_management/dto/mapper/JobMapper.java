package be_im_interview_management.dto.mapper;

import be_im_interview_management.dto.JobDTO;
import be_im_interview_management.entities.Job;

public class JobMapper {
    public JobDTO toJobDTO(Job job) {
        JobDTO dto = new JobDTO();
        dto.setId(job.getId());
        dto.setJobTitle(job.getJobTitle());
        dto.setStartDate(job.getStartDate());
        dto.setEndDate(job.getEndDate());
        dto.setWorkingAddress(job.getWorkingAddress());
        dto.setSkills(job.getSkills());
        dto.setSalaryFrom(job.getSalaryFrom());
        dto.setSalaryTo(job.getSalaryTo());
        dto.setJobStatus(job.getJobStatus());
        dto.setBenefits(job.getBenefits());
        dto.setLevels(job.getLevels());
        dto.setDescription(job.getDescription());
        return dto;
    }
}

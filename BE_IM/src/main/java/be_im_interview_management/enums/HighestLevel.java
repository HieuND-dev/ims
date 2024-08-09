package be_im_interview_management.enums;

/**
 * Created by: HieuND64
 * Date Time: 7/24/2024 10:53 AM
 */
public enum HighestLevel {
    HIGH_SCHOOL("High School"),
    BACHELOR_DEGREE("Bachelor's Degree"),
    MASTER_DEGREE("Master's Degree, PhD");

    private final String label;

    HighestLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

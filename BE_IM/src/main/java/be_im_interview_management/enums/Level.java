package be_im_interview_management.enums;

/**
 * Created by: HieuND64
 * Date Time: 7/24/2024 10:58 AM
 */
public enum Level {
    FRESHER("Fresher"),
    JUNIOR("Junior"),
    SENIOR("Senior"),
    LEADER("Leader"),
    MANAGER("Manager"),
    VICE_HEAD("Vice Head");

    private final String label;

    Level(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

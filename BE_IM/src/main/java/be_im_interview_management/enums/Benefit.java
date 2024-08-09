package be_im_interview_management.enums;

/**
 * Created by: HieuND64
 * Date Time: 7/24/2024 10:55 AM
 */
public enum Benefit {
    LUNCH("Lunch"),
    TWENTY_FIVE_DAY_LEAVE("25-day leave"),
    HEALTHCARE_INSURANCE("Healthcare Insurance"),
    HYBRID_WORKING("Hybrid working"),
    TRAVEL("Travel");

    private final String label;

    Benefit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

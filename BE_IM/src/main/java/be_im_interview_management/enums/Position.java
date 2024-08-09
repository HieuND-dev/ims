package be_im_interview_management.enums;

import lombok.Getter;

/**
 * Created by: HieuND64
 * Date Time: 7/24/2024 10:02 AM
 */
@Getter
public enum Position {
    BACKEND_DEVELOPER("Backend Developer"),
    BUSINESS_ANALYST("Business Analyst"),
    TESTER("Tester"),
    HR("HR"),
    PROJECT_MANAGER("Project Manager"),
    NOT_AVAILABLE("Not available");

    private final String label;

    Position(String label) {
        this.label = label;
    }

}

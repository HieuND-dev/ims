package be_im_interview_management.enums;

/**
 * Created by: HieuND64
 * Date Time: 7/24/2024 10:43 AM
 */
public enum Skill {
    JAVA("Java"),
    NODEJS("NodeJS"),
    DOT_NET(".Net"),
    C_PLUS_PLUS("C++"),
    BUSINESS_ANALYST("Business Analyst"),
    COMMUNICATION("Communication");

    private final String label;

    Skill(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

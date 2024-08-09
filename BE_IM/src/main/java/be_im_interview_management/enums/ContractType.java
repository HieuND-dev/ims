package be_im_interview_management.enums;

/**
 * Created by: HieuND64
 * Date Time: 7/24/2024 11:03 AM
 */
public enum ContractType {

    TRIAL("Trial 2 months"),
    TRAINEE("Trainee 3 months"),
    ONE_YEAR("1 Year"),
    THREE_YEARS("3 years"),
    UNLIMITED("Unlimited");

    private final String label;

    ContractType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

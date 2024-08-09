package be_im_interview_management.enums;

/**
 * Created by: HieuND64
 * Date Time: 7/24/2024 11:01 AM
 */
public enum OfferStatus {
    WAITING_FOR_APPROVAL("Waiting for approval"),
    APPROVED_OFFER("Approved offer"),
    REJECTED_OFFER("Rejected offer"),
    WAITING_FOR_RESPONSE("Waiting for response"),
    ACCEPTED_OFFER("Accepted offer"),
    DECLINED_OFFER("Declined offer"),
    CANCELLED_OFFER("Cancelled offer");

    private final String label;

    OfferStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

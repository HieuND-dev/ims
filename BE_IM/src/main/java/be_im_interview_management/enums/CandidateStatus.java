package be_im_interview_management.enums;

/**
 * Created by: HieuND64
 * Date Time: 7/24/2024 10:46 AM
 */
public enum CandidateStatus {
    WAITING_FOR_INTERVIEW(1, "Waiting for interview"),
    WAITING_FOR_APPROVAL(2, "Waiting for approval"),
    WAITING_FOR_RESPONSE(3, "Waiting for response"),
    OPEN(4, "Open"),
    PASSED_INTERVIEW(5, "Passed interview"),
    APPROVED_OFFER(6, "Approved offer"),
    REJECTED_OFFER(7, "Rejected offer"),
    ACCEPTED_OFFER(8, "Accepted offer"),
    DECLINED_OFFER(9, "Declined offer"),
    CANCELLED_OFFER(10, "Cancelled offer"),
    FAILED_INTERVIEW(11, "Failed interview"),
    CANCELLED_INTERVIEW(12, "Cancelled interview"),
    BANNED(13, "Banned");

    private final int order;
    private final String label;

    CandidateStatus(int order, String label) {
        this.order = order;
        this.label = label;
    }

    public int getOrder() {
        return order;
    }

    public String getLabel() {
        return label;
    }
}

package funny.bunny.xyz.dispute.service.dtos.evidence;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response after evidence submission")
public class EvidenceResponse {

    @Schema(description = "Confirmation message", example = "Evidence added successfully.")
    private String message;

    @Schema(description = "The ID of the dispute to which evidence was added", example = "dsp-67890")
    private String disputeId;

    public EvidenceResponse() {}

    public EvidenceResponse(String message, String disputeId) {
        this.message = message;
        this.disputeId = disputeId;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(String disputeId) {
        this.disputeId = disputeId;
    }
}
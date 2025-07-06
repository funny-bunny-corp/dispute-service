package funny.bunny.xyz.dispute.service.dtos.dispute;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response after dispute operation")
public class DisputeResponse {

    @Schema(description = "The unique ID of the dispute", example = "dsp-67890")
    private String disputeId;

    @Schema(description = "The status of the dispute", example = "pending")
    private String status;

    @Schema(description = "Response message", example = "Dispute created successfully")
    private String message;

    public DisputeResponse() {}

    public DisputeResponse(String disputeId, String status, String message) {
        this.disputeId = disputeId;
        this.status = status;
        this.message = message;
    }

    // Getters and setters
    public String getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(String disputeId) {
        this.disputeId = disputeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
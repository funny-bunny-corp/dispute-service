package funny.bunny.xyz.dispute.service.dtos.action;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to escalate a dispute")
public class RequestAction {

    @NotBlank
    @Schema(description = "Comments explaining the reason for escalation", example = "Merchant did not respond within the allotted time.")
    private String comments;

    public RequestAction() {}

    public RequestAction(String comments) {
        this.comments = comments;
    }

    // Getters and setters
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

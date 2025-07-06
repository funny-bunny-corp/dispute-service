package funny.bunny.xyz.dispute.service.dtos.evidence;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to submit evidence for a dispute")
public class RequestEvidence {

    @NotBlank
    @Schema(description = "The type of evidence being submitted", allowableValues = {"document", "image", "video"}, example = "document")
    private String type;

    @NotBlank
    @Schema(description = "A URL or identifier for the evidence data", example = "https://example.com/evidence/1")
    private String data;

    @NotBlank
    @Schema(description = "The party submitting the evidence", allowableValues = {"customer", "merchant"}, example = "customer")
    private String submittedBy;

    public RequestEvidence() {}

    public RequestEvidence(String type, String data, String submittedBy) {
        this.type = type;
        this.data = data;
        this.submittedBy = submittedBy;
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }
}

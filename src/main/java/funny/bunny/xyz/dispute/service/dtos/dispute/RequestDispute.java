package funny.bunny.xyz.dispute.service.dtos.dispute;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to create a new dispute")
public class RequestDispute {

    @NotBlank
    @Schema(description = "The ID of the transaction being disputed", example = "txn-12345")
    private String transactionId;

    @NotBlank
    @Schema(description = "The reason for the dispute", allowableValues = {"fraudulent", "unauthorized", "item_not_received"}, example = "fraudulent")
    private String reason;

    @NotNull
    @Valid
    @Schema(description = "The initiator of the dispute")
    private DisputeInitiatorDto initiator;

    @Schema(description = "Additional details about the dispute", example = "Unauthorized transaction detected.")
    private String details;

    public RequestDispute() {}

    public RequestDispute(String transactionId, String reason, DisputeInitiatorDto initiator, String details) {
        this.transactionId = transactionId;
        this.reason = reason;
        this.initiator = initiator;
        this.details = details;
    }

    // Getters and setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public DisputeInitiatorDto getInitiator() {
        return initiator;
    }

    public void setInitiator(DisputeInitiatorDto initiator) {
        this.initiator = initiator;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Schema(description = "The initiator of the dispute")
    public static class DisputeInitiatorDto {
        @NotBlank
        @Schema(description = "The type of initiator", allowableValues = {"customer", "merchant"}, example = "customer")
        private String type;

        @NotBlank
        @Schema(description = "The ID of the initiator", example = "cust-98765")
        private String id;

        public DisputeInitiatorDto() {}

        public DisputeInitiatorDto(String type, String id) {
            this.type = type;
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

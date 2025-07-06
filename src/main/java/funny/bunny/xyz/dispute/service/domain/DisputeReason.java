package funny.bunny.xyz.dispute.service.domain;

public class DisputeReason {

  private DisputeReasonCode code;

  private String description;

  public DisputeReason() {}

  public DisputeReason(DisputeReasonCode code, String description) {
    this.code = code;
    this.description = description;
  }

  public DisputeReasonCode getCode() {
    return code;
  }

  public void setCode(DisputeReasonCode code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static DisputeReason fromString(String reasonString) {
    DisputeReasonCode code = switch (reasonString.toLowerCase()) {
      case "fraudulent" -> DisputeReasonCode.FRAUD;
      case "unauthorized" -> DisputeReasonCode.UNAUTHORIZED_TRANSACTION;
      case "item_not_received" -> DisputeReasonCode.ITEM_NOT_RECEIVED;
      default -> DisputeReasonCode.FRAUD;
    };
    return new DisputeReason(code, reasonString);
  }
}

package funny.bunny.xyz.dispute.service.domain;

import java.time.LocalDateTime;

public class Evidence {

  private EvidenceType type;

  private String url;

  private LocalDateTime submittedAt;

  private EvidenceActor submittedBy;

  public Evidence() {
  }

  public Evidence(EvidenceType type, String url, LocalDateTime submittedAt,
      EvidenceActor submittedBy) {
    this.type = type;
    this.url = url;
    this.submittedAt = submittedAt;
    this.submittedBy = submittedBy;
  }

  public EvidenceType getType() {
    return type;
  }

  public String getUrl() {
    return url;
  }

  public LocalDateTime getSubmittedAt() {
    return submittedAt;
  }

  public EvidenceActor getSubmittedBy() {
    return submittedBy;
  }

}

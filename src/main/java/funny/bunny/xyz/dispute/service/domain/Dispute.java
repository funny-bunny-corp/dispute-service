package funny.bunny.xyz.dispute.service.domain;

import funny.bunny.xyz.dispute.service.domain.exceptions.DisputeAlreadyResolvedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Dispute {

  private String id;

  private String transactionId;

  private DisputeReason reason;

  private DisputeStatus status;

  private DisputeInitiator initiator;

  private LocalDateTime createdAt;

  private List<Action> actions;

  private List<Evidence> evidences;

  public Dispute() {
  }

  private Dispute(String id, String transactionId, DisputeReason reason, DisputeStatus status,
      DisputeInitiator initiator, LocalDateTime createdAt) {
    this.id = id;
    this.transactionId = transactionId;
    this.reason = reason;
    this.status = status;
    this.initiator = initiator;
    this.createdAt = createdAt;
  }

  public static Dispute create(String transactionId, DisputeReason reason, DisputeInitiator initiator) {
    return new Dispute(UUID.randomUUID().toString(), transactionId, reason, DisputeStatus.PENDING,
        initiator, LocalDateTime.now());
  }

  public void addEvidence(EvidenceType type, String url, EvidenceActor submittedBy) {
    if (DisputeStatus.RESOLVED.equals(this.status)) {
      throw new DisputeAlreadyResolvedException();
    }
    Evidence evidence = new Evidence(type,url,LocalDateTime.now(),submittedBy);
    this.evidences.add(evidence);
  }

  public void addAction(ActionType type, String comments, ActionActor performedBy) {
    if (DisputeStatus.RESOLVED.equals(this.status)) {
      throw new DisputeAlreadyResolvedException();
    }
    Action action = new Action(UUID.randomUUID().toString(),type,LocalDateTime.now() ,comments, performedBy);
    this.actions.add(action);
  }

}

package funny.bunny.xyz.dispute.service.domain;

import funny.bunny.xyz.dispute.service.domain.exceptions.DisputeAlreadyResolvedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    this.actions = new ArrayList<>();
    this.evidences = new ArrayList<>();
  }

  private Dispute(String id, String transactionId, DisputeReason reason, DisputeStatus status,
      DisputeInitiator initiator, LocalDateTime createdAt) {
    this.id = id;
    this.transactionId = transactionId;
    this.reason = reason;
    this.status = status;
    this.initiator = initiator;
    this.createdAt = createdAt;
    this.actions = new ArrayList<>();
    this.evidences = new ArrayList<>();
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

  public void escalate(String comments) {
    this.status = DisputeStatus.ESCALATED;
    this.addAction(ActionType.ESCALATION, comments, ActionActor.systemActor());
  }

  // Getters and setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public DisputeReason getReason() {
    return reason;
  }

  public void setReason(DisputeReason reason) {
    this.reason = reason;
  }

  public DisputeStatus getStatus() {
    return status;
  }

  public void setStatus(DisputeStatus status) {
    this.status = status;
  }

  public DisputeInitiator getInitiator() {
    return initiator;
  }

  public void setInitiator(DisputeInitiator initiator) {
    this.initiator = initiator;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public List<Action> getActions() {
    return actions;
  }

  public void setActions(List<Action> actions) {
    this.actions = actions;
  }

  public List<Evidence> getEvidences() {
    return evidences;
  }

  public void setEvidences(List<Evidence> evidences) {
    this.evidences = evidences;
  }

}

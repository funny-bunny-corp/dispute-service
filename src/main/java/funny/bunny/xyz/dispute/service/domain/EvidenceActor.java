package funny.bunny.xyz.dispute.service.domain;

public class EvidenceActor {

  private String id;

  private DisputeActorType actor;

  public EvidenceActor() {}

  public EvidenceActor(String id, DisputeActorType actor) {
    this.id = id;
    this.actor = actor;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DisputeActorType getActor() {
    return actor;
  }

  public void setActor(DisputeActorType actor) {
    this.actor = actor;
  }

  public static EvidenceActor fromString(String submittedBy) {
    DisputeActorType actorType = "customer".equalsIgnoreCase(submittedBy) ? 
        DisputeActorType.CUSTOMER : DisputeActorType.MERCHANT;
    return new EvidenceActor(submittedBy, actorType);
  }
}

package funny.bunny.xyz.dispute.service.domain;

public class DisputeInitiator {

  private String id;

  private DisputeActorType actor;

  public DisputeInitiator() {}

  public DisputeInitiator(String id, DisputeActorType actor) {
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

  public static DisputeInitiator fromDto(String initiatorId, String initiatorType) {
    DisputeActorType actorType = "customer".equalsIgnoreCase(initiatorType) ? 
        DisputeActorType.CUSTOMER : DisputeActorType.MERCHANT;
    return new DisputeInitiator(initiatorId, actorType);
  }
}

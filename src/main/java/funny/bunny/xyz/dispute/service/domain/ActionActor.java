package funny.bunny.xyz.dispute.service.domain;

public class ActionActor {

  private String id;

  private ActionActorType actor;

  public ActionActor() {}

  public ActionActor(String id, ActionActorType actor) {
    this.id = id;
    this.actor = actor;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ActionActorType getActor() {
    return actor;
  }

  public void setActor(ActionActorType actor) {
    this.actor = actor;
  }

  public static ActionActor systemActor() {
    return new ActionActor("system", ActionActorType.SYSTEM);
  }
}

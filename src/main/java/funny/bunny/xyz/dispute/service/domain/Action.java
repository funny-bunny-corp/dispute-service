package funny.bunny.xyz.dispute.service.domain;

import java.time.LocalDateTime;

public class Action {

  private String id;

  private ActionType type;

  private LocalDateTime timestamp;

  private String comments;

  private ActionActor actor;

  public Action() {
  }

  public Action(String id, ActionType type, LocalDateTime timestamp, String comments,
      ActionActor actor) {
    this.id = id;
    this.type = type;
    this.timestamp = timestamp;
    this.comments = comments;
    this.actor = actor;
  }

  public String getId() {
    return id;
  }

  public ActionType getType() {
    return type;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String getComments() {
    return comments;
  }

  public ActionActor getActor() {
    return actor;
  }
}

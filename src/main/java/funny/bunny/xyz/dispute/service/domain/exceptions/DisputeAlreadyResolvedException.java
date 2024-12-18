package funny.bunny.xyz.dispute.service.domain.exceptions;

public class DisputeAlreadyResolvedException extends RuntimeException {

  public DisputeAlreadyResolvedException() {
    super("Dispute is already resolved");
  }
}

package funny.bunny.xyz.dispute.service.adapter.http;

import funny.bunny.xyz.dispute.service.domain.application.ReceiveActionService;
import funny.bunny.xyz.dispute.service.domain.application.ReceiveEvidenceService;
import funny.bunny.xyz.dispute.service.domain.application.RequestDisputeService;
import funny.bunny.xyz.dispute.service.dtos.action.RequestAction;
import funny.bunny.xyz.dispute.service.dtos.dispute.RequestDispute;
import funny.bunny.xyz.dispute.service.dtos.evidence.RequestEvidence;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disputes")
public class DisputeResource {

  private final RequestDisputeService requestDisputeService;

  private final ReceiveEvidenceService receiveEvidenceService;

  private final ReceiveActionService receiveActionService;

  public DisputeResource(RequestDisputeService requestDisputeService,
      ReceiveEvidenceService receiveEvidenceService, ReceiveActionService receiveActionService) {
    this.requestDisputeService = requestDisputeService;
    this.receiveEvidenceService = receiveEvidenceService;
    this.receiveActionService = receiveActionService;
  }

  @PostMapping
  public  void requestDispute(@RequestBody RequestDispute requestDispute) {
  }

  @PostMapping("/{id}/evidences")
  public  void requestEvidence(@PathVariable("id")String id, @RequestBody RequestEvidence requestDispute) {
  }

  @PostMapping("/{id}/actions")
  public  void requestAction(@PathVariable("id")String id,@RequestBody RequestAction requestDispute) {
  }

}

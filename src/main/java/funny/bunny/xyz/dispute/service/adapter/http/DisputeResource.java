package funny.bunny.xyz.dispute.service.adapter.http;

import funny.bunny.xyz.dispute.service.domain.application.ReceiveActionService;
import funny.bunny.xyz.dispute.service.domain.application.ReceiveEvidenceService;
import funny.bunny.xyz.dispute.service.domain.application.RequestDisputeService;
import funny.bunny.xyz.dispute.service.dtos.action.RequestAction;
import funny.bunny.xyz.dispute.service.dtos.dispute.RequestDispute;
import funny.bunny.xyz.dispute.service.dtos.dispute.DisputeResponse;
import funny.bunny.xyz.dispute.service.dtos.evidence.RequestEvidence;
import funny.bunny.xyz.dispute.service.dtos.evidence.EvidenceResponse;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disputes")
@Tag(name = "Dispute Management", description = "API for managing disputes")
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
  @Operation(summary = "Create a new dispute", description = "Initiates a new dispute for a specific transaction")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Dispute created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request data")
  })
  public ResponseEntity<DisputeResponse> requestDispute(@Valid @RequestBody RequestDispute requestDispute) {
    DisputeResponse response = requestDisputeService.createDispute(requestDispute);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/{id}/evidence")
  @Operation(summary = "Submit evidence for a dispute", description = "Adds evidence to an existing dispute")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Evidence submitted successfully"),
      @ApiResponse(responseCode = "404", description = "Dispute not found")
  })
  public ResponseEntity<EvidenceResponse> requestEvidence(@PathVariable("id") String id, 
      @Valid @RequestBody RequestEvidence requestEvidence) {
    EvidenceResponse response = receiveEvidenceService.addEvidence(id, requestEvidence);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{id}/escalate")
  @Operation(summary = "Escalate a dispute", description = "Escalates a dispute to a higher authority or system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Dispute escalated successfully"),
      @ApiResponse(responseCode = "404", description = "Dispute not found")
  })
  public ResponseEntity<DisputeResponse> requestAction(@PathVariable("id") String id, 
      @Valid @RequestBody RequestAction requestAction) {
    DisputeResponse response = receiveActionService.escalateDispute(id, requestAction);
    return ResponseEntity.ok(response);
  }

}

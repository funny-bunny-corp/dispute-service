package funny.bunny.xyz.dispute.service.domain.application;

import funny.bunny.xyz.dispute.service.domain.Dispute;
import funny.bunny.xyz.dispute.service.domain.DisputeInitiator;
import funny.bunny.xyz.dispute.service.domain.DisputeReason;
import funny.bunny.xyz.dispute.service.domain.repositories.DisputeRepository;
import funny.bunny.xyz.dispute.service.dtos.dispute.RequestDispute;
import funny.bunny.xyz.dispute.service.dtos.dispute.DisputeResponse;
import org.springframework.stereotype.Service;

@Service
public class RequestDisputeService {

    private final DisputeRepository disputeRepository;

    public RequestDisputeService(DisputeRepository disputeRepository) {
        this.disputeRepository = disputeRepository;
    }

    public DisputeResponse createDispute(RequestDispute request) {
        // Convert DTO to domain objects
        DisputeReason reason = DisputeReason.fromString(request.getReason());
        DisputeInitiator initiator = DisputeInitiator.fromDto(
            request.getInitiator().getId(), 
            request.getInitiator().getType()
        );

        // Create dispute
        Dispute dispute = Dispute.create(request.getTransactionId(), reason, initiator);
        
        // Save dispute
        disputeRepository.add(dispute);

        // Return response
        return new DisputeResponse(
            dispute.getId(),
            dispute.getStatus().name().toLowerCase(),
            "Dispute created successfully"
        );
    }
}

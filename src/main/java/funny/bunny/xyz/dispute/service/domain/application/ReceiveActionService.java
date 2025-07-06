package funny.bunny.xyz.dispute.service.domain.application;

import funny.bunny.xyz.dispute.service.domain.Dispute;
import funny.bunny.xyz.dispute.service.domain.repositories.DisputeRepository;
import funny.bunny.xyz.dispute.service.dtos.action.RequestAction;
import funny.bunny.xyz.dispute.service.dtos.dispute.DisputeResponse;
import org.springframework.stereotype.Service;

@Service
public class ReceiveActionService {

    private final DisputeRepository disputeRepository;

    public ReceiveActionService(DisputeRepository disputeRepository) {
        this.disputeRepository = disputeRepository;
    }

    public DisputeResponse escalateDispute(String disputeId, RequestAction request) {
        // Get dispute
        Dispute dispute = disputeRepository.get(disputeId);
        if (dispute == null) {
            throw new IllegalArgumentException("Dispute not found: " + disputeId);
        }

        // Escalate dispute
        dispute.escalate(request.getComments());

        // Save dispute
        disputeRepository.add(dispute);

        // Return response
        return new DisputeResponse(
            dispute.getId(),
            dispute.getStatus().name().toLowerCase(),
            "Dispute escalated successfully"
        );
    }
}

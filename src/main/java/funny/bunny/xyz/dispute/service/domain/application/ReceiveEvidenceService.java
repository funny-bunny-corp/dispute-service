package funny.bunny.xyz.dispute.service.domain.application;

import funny.bunny.xyz.dispute.service.domain.Dispute;
import funny.bunny.xyz.dispute.service.domain.EvidenceActor;
import funny.bunny.xyz.dispute.service.domain.EvidenceType;
import funny.bunny.xyz.dispute.service.domain.repositories.DisputeRepository;
import funny.bunny.xyz.dispute.service.dtos.evidence.RequestEvidence;
import funny.bunny.xyz.dispute.service.dtos.evidence.EvidenceResponse;
import org.springframework.stereotype.Service;

@Service
public class ReceiveEvidenceService {

    private final DisputeRepository disputeRepository;

    public ReceiveEvidenceService(DisputeRepository disputeRepository) {
        this.disputeRepository = disputeRepository;
    }

    public EvidenceResponse addEvidence(String disputeId, RequestEvidence request) {
        // Get dispute
        Dispute dispute = disputeRepository.get(disputeId);
        if (dispute == null) {
            throw new IllegalArgumentException("Dispute not found: " + disputeId);
        }

        // Convert DTO to domain objects
        EvidenceType type = EvidenceType.valueOf(request.getType().toUpperCase());
        EvidenceActor actor = EvidenceActor.fromString(request.getSubmittedBy());

        // Add evidence
        dispute.addEvidence(type, request.getData(), actor);

        // Save dispute
        disputeRepository.add(dispute);

        // Return response
        return new EvidenceResponse("Evidence added successfully.", disputeId);
    }
}

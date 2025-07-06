package funny.bunny.xyz.dispute.service.domain.application;

import funny.bunny.xyz.dispute.service.domain.Dispute;
import funny.bunny.xyz.dispute.service.domain.DisputeStatus;
import funny.bunny.xyz.dispute.service.domain.DisputeReason;
import funny.bunny.xyz.dispute.service.domain.DisputeReasonCode;
import funny.bunny.xyz.dispute.service.domain.DisputeInitiator;
import funny.bunny.xyz.dispute.service.domain.DisputeActorType;
import funny.bunny.xyz.dispute.service.domain.repositories.DisputeRepository;
import funny.bunny.xyz.dispute.service.dtos.evidence.RequestEvidence;
import funny.bunny.xyz.dispute.service.dtos.evidence.EvidenceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiveEvidenceServiceTest {

    @Mock
    private DisputeRepository disputeRepository;

    private ReceiveEvidenceService receiveEvidenceService;

    @BeforeEach
    void setUp() {
        receiveEvidenceService = new ReceiveEvidenceService(disputeRepository);
    }

    @Test
    void addEvidence_ShouldAddDocumentEvidence() {
        // Given
        String disputeId = "dispute-123";
        RequestEvidence request = new RequestEvidence("document", "https://example.com/doc.pdf", "customer");
        
        Dispute dispute = createTestDispute(disputeId);
        when(disputeRepository.get(disputeId)).thenReturn(dispute);

        // When
        EvidenceResponse response = receiveEvidenceService.addEvidence(disputeId, request);

        // Then
        assertNotNull(response);
        assertEquals("Evidence added successfully.", response.getMessage());
        assertEquals(disputeId, response.getDisputeId());
        
        verify(disputeRepository).add(dispute);
        assertEquals(1, dispute.getEvidences().size());
    }

    @Test
    void addEvidence_ShouldAddImageEvidence() {
        // Given
        String disputeId = "dispute-456";
        RequestEvidence request = new RequestEvidence("image", "https://example.com/photo.jpg", "merchant");
        
        Dispute dispute = createTestDispute(disputeId);
        when(disputeRepository.get(disputeId)).thenReturn(dispute);

        // When
        EvidenceResponse response = receiveEvidenceService.addEvidence(disputeId, request);

        // Then
        assertNotNull(response);
        assertEquals("Evidence added successfully.", response.getMessage());
        assertEquals(disputeId, response.getDisputeId());
        
        verify(disputeRepository).add(dispute);
        assertEquals(1, dispute.getEvidences().size());
    }

    @Test
    void addEvidence_ShouldAddVideoEvidence() {
        // Given
        String disputeId = "dispute-789";
        RequestEvidence request = new RequestEvidence("video", "https://example.com/video.mp4", "customer");
        
        Dispute dispute = createTestDispute(disputeId);
        when(disputeRepository.get(disputeId)).thenReturn(dispute);

        // When
        EvidenceResponse response = receiveEvidenceService.addEvidence(disputeId, request);

        // Then
        assertNotNull(response);
        assertEquals("Evidence added successfully.", response.getMessage());
        assertEquals(disputeId, response.getDisputeId());
        
        verify(disputeRepository).add(dispute);
        assertEquals(1, dispute.getEvidences().size());
    }

    @Test
    void addEvidence_ShouldThrowExceptionWhenDisputeNotFound() {
        // Given
        String disputeId = "non-existent-dispute";
        RequestEvidence request = new RequestEvidence("document", "https://example.com/doc.pdf", "customer");
        
        when(disputeRepository.get(disputeId)).thenReturn(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> receiveEvidenceService.addEvidence(disputeId, request));
        
        assertEquals("Dispute not found: " + disputeId, exception.getMessage());
        verify(disputeRepository, never()).add(any());
    }

    @Test
    void addEvidence_ShouldHandleMultipleEvidences() {
        // Given
        String disputeId = "dispute-multi";
        RequestEvidence request1 = new RequestEvidence("document", "https://example.com/doc1.pdf", "customer");
        RequestEvidence request2 = new RequestEvidence("image", "https://example.com/img1.jpg", "merchant");
        
        Dispute dispute = createTestDispute(disputeId);
        when(disputeRepository.get(disputeId)).thenReturn(dispute);

        // When
        receiveEvidenceService.addEvidence(disputeId, request1);
        receiveEvidenceService.addEvidence(disputeId, request2);

        // Then
        assertEquals(2, dispute.getEvidences().size());
        verify(disputeRepository, times(2)).add(dispute);
    }

    private Dispute createTestDispute(String disputeId) {
        Dispute dispute = new Dispute();
        dispute.setId(disputeId);
        dispute.setTransactionId("txn-123");
        dispute.setStatus(DisputeStatus.PENDING);
        dispute.setReason(new DisputeReason(DisputeReasonCode.FRAUD, "fraudulent"));
        dispute.setInitiator(new DisputeInitiator("cust-123", DisputeActorType.CUSTOMER));
        return dispute;
    }
}
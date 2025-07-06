package funny.bunny.xyz.dispute.service.domain.application;

import funny.bunny.xyz.dispute.service.domain.Dispute;
import funny.bunny.xyz.dispute.service.domain.DisputeStatus;
import funny.bunny.xyz.dispute.service.domain.DisputeReason;
import funny.bunny.xyz.dispute.service.domain.DisputeReasonCode;
import funny.bunny.xyz.dispute.service.domain.DisputeInitiator;
import funny.bunny.xyz.dispute.service.domain.DisputeActorType;
import funny.bunny.xyz.dispute.service.domain.repositories.DisputeRepository;
import funny.bunny.xyz.dispute.service.dtos.action.RequestAction;
import funny.bunny.xyz.dispute.service.dtos.dispute.DisputeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiveActionServiceTest {

    @Mock
    private DisputeRepository disputeRepository;

    private ReceiveActionService receiveActionService;

    @BeforeEach
    void setUp() {
        receiveActionService = new ReceiveActionService(disputeRepository);
    }

    @Test
    void escalateDispute_ShouldEscalateSuccessfully() {
        // Given
        String disputeId = "dispute-123";
        RequestAction request = new RequestAction("Merchant did not respond within the allotted time.");
        
        Dispute dispute = createTestDispute(disputeId);
        when(disputeRepository.get(disputeId)).thenReturn(dispute);

        // When
        DisputeResponse response = receiveActionService.escalateDispute(disputeId, request);

        // Then
        assertNotNull(response);
        assertEquals(disputeId, response.getDisputeId());
        assertEquals("escalated", response.getStatus());
        assertEquals("Dispute escalated successfully", response.getMessage());
        
        verify(disputeRepository).add(dispute);
        assertEquals(DisputeStatus.ESCALATED, dispute.getStatus());
        assertEquals(1, dispute.getActions().size());
    }

    @Test
    void escalateDispute_ShouldThrowExceptionWhenDisputeNotFound() {
        // Given
        String disputeId = "non-existent-dispute";
        RequestAction request = new RequestAction("Some escalation reason");
        
        when(disputeRepository.get(disputeId)).thenReturn(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> receiveActionService.escalateDispute(disputeId, request));
        
        assertEquals("Dispute not found: " + disputeId, exception.getMessage());
        verify(disputeRepository, never()).add(any());
    }

    @Test
    void escalateDispute_ShouldHandleMultipleEscalations() {
        // Given
        String disputeId = "dispute-multi";
        RequestAction request1 = new RequestAction("First escalation reason");
        RequestAction request2 = new RequestAction("Second escalation reason");
        
        Dispute dispute = createTestDispute(disputeId);
        when(disputeRepository.get(disputeId)).thenReturn(dispute);

        // When
        receiveActionService.escalateDispute(disputeId, request1);
        receiveActionService.escalateDispute(disputeId, request2);

        // Then
        assertEquals(DisputeStatus.ESCALATED, dispute.getStatus());
        assertEquals(2, dispute.getActions().size());
        verify(disputeRepository, times(2)).add(dispute);
    }

    @Test
    void escalateDispute_ShouldUpdateStatusFromPendingToEscalated() {
        // Given
        String disputeId = "dispute-pending";
        RequestAction request = new RequestAction("Need management review");
        
        Dispute dispute = createTestDispute(disputeId);
        dispute.setStatus(DisputeStatus.PENDING);
        when(disputeRepository.get(disputeId)).thenReturn(dispute);

        // When
        DisputeResponse response = receiveActionService.escalateDispute(disputeId, request);

        // Then
        assertEquals(DisputeStatus.ESCALATED, dispute.getStatus());
        assertEquals("escalated", response.getStatus());
        verify(disputeRepository).add(dispute);
    }

    @Test
    void escalateDispute_ShouldPreserveDisputeDetails() {
        // Given
        String disputeId = "dispute-preserve";
        RequestAction request = new RequestAction("Escalation with detailed comments");
        
        Dispute dispute = createTestDispute(disputeId);
        String originalTransactionId = dispute.getTransactionId();
        DisputeReason originalReason = dispute.getReason();
        DisputeInitiator originalInitiator = dispute.getInitiator();
        
        when(disputeRepository.get(disputeId)).thenReturn(dispute);

        // When
        receiveActionService.escalateDispute(disputeId, request);

        // Then
        assertEquals(originalTransactionId, dispute.getTransactionId());
        assertEquals(originalReason, dispute.getReason());
        assertEquals(originalInitiator, dispute.getInitiator());
        assertEquals(DisputeStatus.ESCALATED, dispute.getStatus());
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
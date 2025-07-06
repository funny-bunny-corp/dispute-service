package funny.bunny.xyz.dispute.service.domain.application;

import funny.bunny.xyz.dispute.service.domain.Dispute;
import funny.bunny.xyz.dispute.service.domain.DisputeStatus;
import funny.bunny.xyz.dispute.service.domain.repositories.DisputeRepository;
import funny.bunny.xyz.dispute.service.dtos.dispute.RequestDispute;
import funny.bunny.xyz.dispute.service.dtos.dispute.DisputeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestDisputeServiceTest {

    @Mock
    private DisputeRepository disputeRepository;

    private RequestDisputeService requestDisputeService;

    @BeforeEach
    void setUp() {
        requestDisputeService = new RequestDisputeService(disputeRepository);
    }

    @Test
    void createDispute_ShouldCreateValidDispute() {
        // Given
        RequestDispute.DisputeInitiatorDto initiator = new RequestDispute.DisputeInitiatorDto("customer", "cust-123");
        RequestDispute request = new RequestDispute("txn-123", "fraudulent", initiator, "Test details");

        // When
        DisputeResponse response = requestDisputeService.createDispute(request);

        // Then
        assertNotNull(response);
        assertNotNull(response.getDisputeId());
        assertEquals("pending", response.getStatus());
        assertEquals("Dispute created successfully", response.getMessage());

        // Verify repository interaction
        ArgumentCaptor<Dispute> disputeCaptor = ArgumentCaptor.forClass(Dispute.class);
        verify(disputeRepository).add(disputeCaptor.capture());
        
        Dispute capturedDispute = disputeCaptor.getValue();
        assertEquals("txn-123", capturedDispute.getTransactionId());
        assertEquals(DisputeStatus.PENDING, capturedDispute.getStatus());
        assertNotNull(capturedDispute.getId());
        assertNotNull(capturedDispute.getCreatedAt());
    }

    @Test
    void createDispute_ShouldHandleMerchantInitiator() {
        // Given
        RequestDispute.DisputeInitiatorDto initiator = new RequestDispute.DisputeInitiatorDto("merchant", "merchant-456");
        RequestDispute request = new RequestDispute("txn-456", "unauthorized", initiator, "Merchant dispute");

        // When
        DisputeResponse response = requestDisputeService.createDispute(request);

        // Then
        assertNotNull(response);
        assertEquals("pending", response.getStatus());
        
        ArgumentCaptor<Dispute> disputeCaptor = ArgumentCaptor.forClass(Dispute.class);
        verify(disputeRepository).add(disputeCaptor.capture());
        
        Dispute capturedDispute = disputeCaptor.getValue();
        assertEquals("merchant-456", capturedDispute.getInitiator().getId());
    }

    @Test
    void createDispute_ShouldHandleItemNotReceivedReason() {
        // Given
        RequestDispute.DisputeInitiatorDto initiator = new RequestDispute.DisputeInitiatorDto("customer", "cust-789");
        RequestDispute request = new RequestDispute("txn-789", "item_not_received", initiator, "Item not received");

        // When
        DisputeResponse response = requestDisputeService.createDispute(request);

        // Then
        assertNotNull(response);
        assertEquals("pending", response.getStatus());
        
        ArgumentCaptor<Dispute> disputeCaptor = ArgumentCaptor.forClass(Dispute.class);
        verify(disputeRepository).add(disputeCaptor.capture());
        
        Dispute capturedDispute = disputeCaptor.getValue();
        assertEquals("item_not_received", capturedDispute.getReason().getDescription());
    }
}
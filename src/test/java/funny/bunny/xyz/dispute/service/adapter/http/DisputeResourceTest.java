package funny.bunny.xyz.dispute.service.adapter.http;

import funny.bunny.xyz.dispute.service.domain.application.ReceiveActionService;
import funny.bunny.xyz.dispute.service.domain.application.ReceiveEvidenceService;
import funny.bunny.xyz.dispute.service.domain.application.RequestDisputeService;
import funny.bunny.xyz.dispute.service.dtos.dispute.DisputeResponse;
import funny.bunny.xyz.dispute.service.dtos.evidence.EvidenceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DisputeResource.class)
class DisputeResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RequestDisputeService requestDisputeService;

    @MockBean
    private ReceiveEvidenceService receiveEvidenceService;

    @MockBean
    private ReceiveActionService receiveActionService;

    @Test
    void createDispute_ShouldReturnCreated() throws Exception {
        // Given
        String requestBody = """
            {
                "transactionId": "txn-123",
                "reason": "fraudulent",
                "initiator": {
                    "type": "customer",
                    "id": "cust-123"
                },
                "details": "Unauthorized transaction"
            }
            """;

        DisputeResponse mockResponse = new DisputeResponse("dispute-123", "pending", "Dispute created successfully");
        when(requestDisputeService.createDispute(any())).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/disputes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.disputeId").value("dispute-123"))
                .andExpect(jsonPath("$.status").value("pending"))
                .andExpect(jsonPath("$.message").value("Dispute created successfully"));
    }

    @Test
    void createDispute_ShouldReturnBadRequestForInvalidData() throws Exception {
        // Given
        String requestBody = """
            {
                "transactionId": "",
                "reason": "invalid_reason",
                "initiator": {
                    "type": "",
                    "id": ""
                }
            }
            """;

        // When & Then
        mockMvc.perform(post("/disputes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void submitEvidence_ShouldReturnOk() throws Exception {
        // Given
        String disputeId = "dispute-123";
        String requestBody = """
            {
                "type": "document",
                "data": "https://example.com/evidence.pdf",
                "submittedBy": "customer"
            }
            """;

        EvidenceResponse mockResponse = new EvidenceResponse("Evidence added successfully.", disputeId);
        when(receiveEvidenceService.addEvidence(eq(disputeId), any())).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/disputes/" + disputeId + "/evidence")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Evidence added successfully."))
                .andExpect(jsonPath("$.disputeId").value(disputeId));
    }

    @Test
    void submitEvidence_ShouldReturnBadRequestForInvalidData() throws Exception {
        // Given
        String disputeId = "dispute-123";
        String requestBody = """
            {
                "type": "",
                "data": "",
                "submittedBy": ""
            }
            """;

        // When & Then
        mockMvc.perform(post("/disputes/" + disputeId + "/evidence")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void escalateDispute_ShouldReturnOk() throws Exception {
        // Given
        String disputeId = "dispute-123";
        String requestBody = """
            {
                "comments": "Merchant did not respond within the allotted time."
            }
            """;

        DisputeResponse mockResponse = new DisputeResponse(disputeId, "escalated", "Dispute escalated successfully");
        when(receiveActionService.escalateDispute(eq(disputeId), any())).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(patch("/disputes/" + disputeId + "/escalate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.disputeId").value(disputeId))
                .andExpect(jsonPath("$.status").value("escalated"))
                .andExpect(jsonPath("$.message").value("Dispute escalated successfully"));
    }

    @Test
    void escalateDispute_ShouldReturnBadRequestForInvalidData() throws Exception {
        // Given
        String disputeId = "dispute-123";
        String requestBody = """
            {
                "comments": ""
            }
            """;

        // When & Then
        mockMvc.perform(patch("/disputes/" + disputeId + "/escalate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createDispute_ShouldHandleMerchantInitiator() throws Exception {
        // Given
        String requestBody = """
            {
                "transactionId": "txn-456",
                "reason": "unauthorized",
                "initiator": {
                    "type": "merchant",
                    "id": "merchant-456"
                },
                "details": "Merchant dispute"
            }
            """;

        DisputeResponse mockResponse = new DisputeResponse("dispute-456", "pending", "Dispute created successfully");
        when(requestDisputeService.createDispute(any())).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/disputes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.disputeId").value("dispute-456"))
                .andExpect(jsonPath("$.status").value("pending"));
    }

    @Test
    void submitEvidence_ShouldHandleImageEvidence() throws Exception {
        // Given
        String disputeId = "dispute-456";
        String requestBody = """
            {
                "type": "image",
                "data": "https://example.com/photo.jpg",
                "submittedBy": "merchant"
            }
            """;

        EvidenceResponse mockResponse = new EvidenceResponse("Evidence added successfully.", disputeId);
        when(receiveEvidenceService.addEvidence(eq(disputeId), any())).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/disputes/" + disputeId + "/evidence")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Evidence added successfully."))
                .andExpect(jsonPath("$.disputeId").value(disputeId));
    }
}
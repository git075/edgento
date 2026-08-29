/*
 * WHAT: Mapper for AuditConversation entities and AuditReport entities to DTOs.
 * WHY:  Keeps mapping logic out of the service, and service logic out of the mapper.
 */
package com.edgento.api.mapper;

import com.edgento.api.model.dto.response.AuditReportResponse;
import com.edgento.api.model.dto.response.ConversationResponse;
import com.edgento.api.model.entity.AuditConversation;
import com.edgento.api.model.entity.AuditReport;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConversationMapper {

    private final ObjectMapper objectMapper;

    /**
     * Converts AuditConversation entity + AI message into a ConversationResponse DTO.
     */
    public ConversationResponse toResponse(AuditConversation conversation,
                                           String message, String extractedDataJson) {
        Object parsedData = null;
        if (extractedDataJson != null && !extractedDataJson.isBlank()) {
            try {
                parsedData = objectMapper.readValue(extractedDataJson, Object.class);
            } catch (Exception e) {
                log.warn("Could not parse extracted data JSON: {}", extractedDataJson);
            }
        }

        return new ConversationResponse(
                conversation.getId(),
                conversation.getCurrentStep(),
                conversation.getStatus(),
                message,
                parsedData
        );
    }

    /**
     * Converts AuditReport entity into an AuditReportResponse DTO.
     */
    public AuditReportResponse toReportResponse(AuditReport report) {
        List<String> vulnerabilities = parseJsonArray(report.getVulnerabilities());
        List<String> recommendations = parseJsonArray(report.getRecommendations());

        return new AuditReportResponse(
                report.getId(),
                report.getConversation().getId(),
                report.getHealthScore(),
                vulnerabilities,
                report.getRevenueGapEstimate(),
                recommendations,
                report.getGeneratedAt()
        );
    }

    /**
     * Safely parses a JSON array string (e.g., '["item1", "item2"]') into a List<String>.
     */
    private List<String> parseJsonArray(String jsonArray) {
        if (jsonArray == null || jsonArray.isBlank()) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(jsonArray, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            log.warn("Could not parse JSON array: {}", jsonArray);
            return Collections.emptyList();
        }
    }
}

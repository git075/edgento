/*
 * WHAT: Service class containing all business logic for Leads.
 * WHY:  Keeps the Controller thin (only handling HTTP) and the Repository thin (only DB queries).
 * HOW:  Uses Constructor Injection to get the Repository and Mapper, then orchestrates them.
 */
package com.edgento.api.service;

import com.edgento.api.exception.ResourceNotFoundException;
import com.edgento.api.mapper.LeadMapper;
import com.edgento.api.model.dto.request.CreateLeadRequest;
import com.edgento.api.model.dto.response.LeadResponse;
import com.edgento.api.model.entity.Lead;
import com.edgento.api.repository.LeadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

// 📚 CONCEPT: @RequiredArgsConstructor
// A Lombok annotation that automatically generates a constructor with arguments
// for all 'private final' fields. This is the modern Spring way to do Dependency Injection
// (instead of using @Autowired on fields).
@Service
@RequiredArgsConstructor
public class LeadService {

    private final LeadRepository leadRepository;
    private final LeadMapper leadMapper;

    /**
     * Creates a new lead in the database.
     * @param request the validated incoming lead data
     * @return the saved lead as a Response DTO
     */
    public LeadResponse createLead(CreateLeadRequest request) {
        // 1. Convert incoming DTO to Entity
        Lead leadEntity = leadMapper.toEntity(request);

        // 2. Save Entity to Database
        Lead savedLead = leadRepository.save(leadEntity);

        // 3. Convert saved Entity back to Response DTO
        return leadMapper.toResponse(savedLead);
    }

    /**
     * Retrieves a paginated list of all leads.
     */
    public Page<LeadResponse> getAllLeads(Pageable pageable) {
        return leadRepository.findAll(pageable)
                .map(leadMapper::toResponse);
    }

    /**
     * Retrieves a single lead by its UUID.
     */
    public LeadResponse getLeadById(UUID id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found with id: " + id));
        return leadMapper.toResponse(lead);
    }
}

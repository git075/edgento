/*
 * WHAT: REST Controller for managing Leads.
 * WHY:  Handles HTTP requests (POST /api/v1/leads), validates them, and delegates to LeadService.
 * HOW:  Annotated with @RestController and @RequestMapping.
 */
package com.edgento.api.controller;

import com.edgento.api.model.dto.request.CreateLeadRequest;
import com.edgento.api.model.dto.response.LeadResponse;
import com.edgento.api.service.LeadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/leads")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;

    // POST /api/v1/leads
    @PostMapping
    public ResponseEntity<LeadResponse> createLead(@Valid @RequestBody CreateLeadRequest request) {
        LeadResponse response = leadService.createLead(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/v1/leads
    @GetMapping
    public ResponseEntity<Page<LeadResponse>> getAllLeads(Pageable pageable) {
        Page<LeadResponse> response = leadService.getAllLeads(pageable);
        return ResponseEntity.ok(response);
    }

    // GET /api/v1/leads/{id}
    @GetMapping("/{id}")
    public ResponseEntity<LeadResponse> getLeadById(@PathVariable UUID id) {
        LeadResponse response = leadService.getLeadById(id);
        return ResponseEntity.ok(response);
    }
}

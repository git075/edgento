/**
 * WHAT: Controller handling lead-related operations.
 * WHY: Exposes REST APIs for creating and fetching leads.
 * HOW: Injects LeadService to perform business logic.
 */
package com.edgento.api.controller;

// 📚 CONCEPT: @RestController - A convenience annotation that is itself annotated with @Controller and @ResponseBody.
import com.edgento.api.model.dto.request.CreateLeadRequest;
import com.edgento.api.model.dto.response.LeadResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Marks this class as a controller where every method returns a domain object instead of a view.
@RequestMapping("/api/v1/leads") // Maps HTTP requests to handler methods of MVC and REST controllers.
public class LeadController {

    /**
     * Creates a new lead.
     * @param request DTO containing lead details.
     * @return ResponseEntity with the created lead response.
     */
    @PostMapping // Maps HTTP POST requests onto specific handler methods.
    public ResponseEntity<LeadResponse> createLead(@RequestBody CreateLeadRequest request) { // @RequestBody indicates a method parameter should be bound to the body of the web request.
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

package com.webApp.blog.controller;

import com.webApp.blog.dto.request.ContactRequestDTO;
import com.webApp.blog.dto.response.ApiMessageResponseDTO;
import com.webApp.blog.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ApiMessageResponseDTO> submitContact(@Valid @RequestBody ContactRequestDTO request) {
        contactService.submit(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiMessageResponseDTO("Thank you for contacting us."));
    }
}

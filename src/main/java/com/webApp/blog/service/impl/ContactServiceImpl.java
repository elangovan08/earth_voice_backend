package com.webApp.blog.service.impl;

import com.webApp.blog.dto.request.ContactRequestDTO;
import com.webApp.blog.event.ContactSubmittedEvent;
import com.webApp.blog.model.Contact;
import com.webApp.blog.repository.ContactRepository;
import com.webApp.blog.service.ContactService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ContactServiceImpl(ContactRepository contactRepository, ApplicationEventPublisher eventPublisher) {
        this.contactRepository = contactRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public void submit(ContactRequestDTO request) {
        Contact contact = new Contact();
        contact.setName(request.getName());
        contact.setEmail(request.getEmail());
        contact.setSubject(request.getSubject());
        contact.setMessage(request.getMessage());
        Contact savedContact = contactRepository.save(contact);
        eventPublisher.publishEvent(ContactSubmittedEvent.from(savedContact));
    }
}

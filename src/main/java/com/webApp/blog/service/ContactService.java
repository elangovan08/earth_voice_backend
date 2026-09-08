package com.webApp.blog.service;

import com.webApp.blog.dto.request.ContactRequestDTO;

public interface ContactService {

    void submit(ContactRequestDTO request);
}

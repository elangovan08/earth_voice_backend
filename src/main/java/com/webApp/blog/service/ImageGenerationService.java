package com.webApp.blog.service;

public interface ImageGenerationService {

    GeneratedImage generateImage(String title, String content, String category);
}

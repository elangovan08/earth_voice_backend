package com.webApp.blog.service.impl;

import com.webApp.blog.config.HuggingFaceProperties;
import com.webApp.blog.service.DefaultPostImageService;
import com.webApp.blog.service.GeneratedImage;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class HuggingFaceImageGenerationServiceTest {

    @Test
    void returnsHuggingFaceImageBytesWhenProviderResponds() {
        HuggingFaceProperties properties = configuredProperties();
        RestClient.Builder restClientBuilder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(restClientBuilder).build();
        byte[] imageBytes = new byte[]{1, 2, 3, 4};

        server.expect(once(), requestTo("https://hf.test/models/test-org/test-model"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(HttpHeaders.AUTHORIZATION, "Bearer hf_test"))
                .andRespond(withSuccess(imageBytes, MediaType.IMAGE_PNG));

        HuggingFaceImageGenerationService service = new HuggingFaceImageGenerationService(
                properties,
                restClientBuilder,
                new DefaultPostImageService()
        );

        GeneratedImage generatedImage = service.generateImage("Solar homes", "Rooftop solar in cities", "Energy");

        assertArrayEquals(imageBytes, generatedImage.imageData());
        assertEquals(MediaType.IMAGE_PNG_VALUE, generatedImage.contentType());
        server.verify();
    }

    @Test
    void fallsBackToCategoryDefaultWhenHuggingFaceIsNotConfigured() {
        HuggingFaceProperties properties = new HuggingFaceProperties();
        properties.setApiToken("");
        properties.setImageModel("");

        HuggingFaceImageGenerationService service = new HuggingFaceImageGenerationService(
                properties,
                RestClient.builder(),
                new DefaultPostImageService()
        );

        GeneratedImage generatedImage = service.generateImage("Forest recovery", "Protecting habitats", "Wildlife");
        String svg = new String(generatedImage.imageData(), StandardCharsets.UTF_8);

        assertEquals("image/svg+xml", generatedImage.contentType());
        assertTrue(svg.startsWith("<svg"));
        assertTrue(svg.contains("Wildlife"));
        assertTrue(svg.contains("Forest recovery"));
    }

    private HuggingFaceProperties configuredProperties() {
        HuggingFaceProperties properties = new HuggingFaceProperties();
        properties.setApiToken("hf_test");
        properties.setImageUrl("https://hf.test/models");
        properties.setImageModel("test-org/test-model");
        return properties;
    }
}

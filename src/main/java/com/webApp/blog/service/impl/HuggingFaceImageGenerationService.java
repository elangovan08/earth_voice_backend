package com.webApp.blog.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webApp.blog.config.HuggingFaceProperties;
import com.webApp.blog.service.DefaultPostImageService;
import com.webApp.blog.service.GeneratedImage;
import com.webApp.blog.service.ImageGenerationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.net.URI;

@Service
public class HuggingFaceImageGenerationService implements ImageGenerationService {

    private final HuggingFaceProperties properties;
    private final RestClient restClient;
    private final DefaultPostImageService defaultPostImageService;

    public HuggingFaceImageGenerationService(
            HuggingFaceProperties properties,
            RestClient.Builder restClientBuilder,
            DefaultPostImageService defaultPostImageService
    ) {
        this.properties = properties;
        this.restClient = restClientBuilder.build();
        this.defaultPostImageService = defaultPostImageService;
    }

    @Override
    public GeneratedImage generateImage(String title, String content, String category) {
        if (properties.isConfigured()) {
            try {
                GeneratedImage generatedImage = generateWithHuggingFace(title, content, category);
                if (generatedImage != null && generatedImage.imageData() != null && generatedImage.imageData().length > 0) {
                    return generatedImage;
                }
            } catch (RestClientException | IllegalArgumentException ex) {
                System.err.println("Hugging Face image generation failed: " + ex.getMessage());
            }
        }

        return defaultPostImageService.generateDefaultImage(title, category);
    }

    private GeneratedImage generateWithHuggingFace(String title, String content, String category) {
        ResponseEntity<byte[]> response = restClient.post()
                .uri(URI.create(properties.getImageEndpoint()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + properties.getApiToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.IMAGE_PNG, MediaType.IMAGE_JPEG, MediaType.APPLICATION_OCTET_STREAM)
                .body(new HuggingFaceImageRequest(
                        buildPrompt(title, content, category),
                        new HuggingFaceImageParameters(
                                properties.getNegativePrompt(),
                                properties.getNumInferenceSteps(),
                                properties.getGuidanceScale(),
                                properties.getWidth(),
                                properties.getHeight()
                        )
                ))
                .retrieve()
                .toEntity(byte[].class);

        byte[] imageData = response.getBody();
        if (imageData == null || imageData.length == 0 || isJsonResponse(response)) {
            return null;
        }

        return new GeneratedImage(imageData, resolveContentType(response));
    }

    private boolean isJsonResponse(ResponseEntity<byte[]> response) {
        MediaType contentType = response.getHeaders().getContentType();
        return contentType != null && MediaType.APPLICATION_JSON.isCompatibleWith(contentType);
    }

    private String resolveContentType(ResponseEntity<byte[]> response) {
        MediaType contentType = response.getHeaders().getContentType();
        if (contentType != null && contentType.isCompatibleWith(MediaType.IMAGE_JPEG)) {
            return MediaType.IMAGE_JPEG_VALUE;
        }
        if (contentType != null && contentType.isCompatibleWith(MediaType.IMAGE_PNG)) {
            return MediaType.IMAGE_PNG_VALUE;
        }
        if (contentType != null && "image".equals(contentType.getType())) {
            return contentType.toString();
        }
        return MediaType.IMAGE_PNG_VALUE;
    }

    private String buildPrompt(String title, String content, String category) {
        String safeTitle = StringUtils.hasText(title) ? title.trim() : "EarthVoice post";
        String safeContent = StringUtils.hasText(content) ? content.trim() : "sustainable living";
        String safeCategory = StringUtils.hasText(category) ? category.trim() : "environment";
        if (safeContent.length() > 500) {
            safeContent = safeContent.substring(0, 500);
        }

        return """
                Create a high quality 16:9 eco-conscious blog cover image.
                Category: %s.
                Title: %s.
                Content summary: %s.
                Style: realistic editorial photography, natural lighting, no text, no watermark, no logo.
                """.formatted(safeCategory, safeTitle, safeContent);
    }

    private record HuggingFaceImageRequest(String inputs, HuggingFaceImageParameters parameters) {
    }

    private record HuggingFaceImageParameters(
            @JsonProperty("negative_prompt") String negativePrompt,
            @JsonProperty("num_inference_steps") int numInferenceSteps,
            @JsonProperty("guidance_scale") double guidanceScale,
            int width,
            int height
    ) {
    }
}

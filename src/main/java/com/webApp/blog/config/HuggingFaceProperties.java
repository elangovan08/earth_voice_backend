package com.webApp.blog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConfigurationProperties(prefix = "huggingface")
public class HuggingFaceProperties {

    private String apiToken = "";
    private String imageModel = "stabilityai/stable-diffusion-xl-base-1.0";
    private String imageUrl = "https://api-inference.huggingface.co/models";
    private int width = 1024;
    private int height = 576;
    private int numInferenceSteps = 25;
    private double guidanceScale = 7.0;
    private String negativePrompt = "blurry, low quality, distorted, text artifacts";

    public boolean isConfigured() {
        return StringUtils.hasText(apiToken) && StringUtils.hasText(imageModel);
    }

    public String getImageEndpoint() {
        String baseUrl = imageUrl == null ? "" : imageUrl.trim();
        while (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        return baseUrl + "/" + imageModel.trim();
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getImageModel() {
        return imageModel;
    }

    public void setImageModel(String imageModel) {
        this.imageModel = imageModel;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNumInferenceSteps() {
        return numInferenceSteps;
    }

    public void setNumInferenceSteps(int numInferenceSteps) {
        this.numInferenceSteps = numInferenceSteps;
    }

    public double getGuidanceScale() {
        return guidanceScale;
    }

    public void setGuidanceScale(double guidanceScale) {
        this.guidanceScale = guidanceScale;
    }

    public String getNegativePrompt() {
        return negativePrompt;
    }

    public void setNegativePrompt(String negativePrompt) {
        this.negativePrompt = negativePrompt;
    }
}

package com.example.wordInversion.dto;

import com.example.wordInversion.entity.WordInversion;

import java.time.LocalDateTime;

public class InversionReponse {

    private Long id;
    private String request;
    private String response;
    private LocalDateTime createdAt;
    private boolean isCached; //true if reponse already exists

    public InversionReponse(WordInversion entity) {
        this.id = entity.getId();
        this.request = entity.getRequestSentence();
        this.response = entity.getResponseSentence();
        this.createdAt = entity.getCreatedAt();
        this.isCached = false;
    }

    public InversionReponse(WordInversion entity, boolean isCached) {
        this.id = entity.getId();
        this.request = entity.getRequestSentence();
        this.response = entity.getResponseSentence();
        this.createdAt = entity.getCreatedAt();
        this.isCached = isCached;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCached() {
        return isCached;
    }

    public void setCached(boolean cached) {
        isCached = cached;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

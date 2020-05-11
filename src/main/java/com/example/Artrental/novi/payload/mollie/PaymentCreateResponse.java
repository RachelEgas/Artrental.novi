package com.example.Artrental.novi.payload.mollie;

import com.example.Artrental.novi.payload.mollie.Json._links;
import com.example.Artrental.novi.payload.mollie.Json.amount;
import com.example.Artrental.novi.payload.mollie.Json.metadata;

import java.time.Instant;

public class PaymentCreateResponse {
    private String resource;
    private String id;
    private String mode;
    private Instant createdAt;
    private amount amount;
    private String description;
    private String method;
    private metadata metadata;
    private String status;
    private Boolean isCancelable;
    private Instant expiresAt;
    private String details;
    private String profileId;
    private String sequenceType;
    private String redirectUrl;
    private String webhookUrl;
    private _links _links;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public com.example.Artrental.novi.payload.mollie.Json.amount getAmount() {
        return amount;
    }

    public void setAmount(com.example.Artrental.novi.payload.mollie.Json.amount amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public com.example.Artrental.novi.payload.mollie.Json.metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(com.example.Artrental.novi.payload.mollie.Json.metadata metadata) {
        this.metadata = metadata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getCancelable() {
        return isCancelable;
    }

    public void setCancelable(Boolean cancelable) {
        isCancelable = cancelable;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getSequenceType() {
        return sequenceType;
    }

    public void setSequenceType(String sequenceType) {
        this.sequenceType = sequenceType;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public com.example.Artrental.novi.payload.mollie.Json._links get_links() {
        return _links;
    }

    public void set_links(com.example.Artrental.novi.payload.mollie.Json._links _links) {
        this._links = _links;
    }
}

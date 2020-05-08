package com.example.Artrental.novi.payload.mollie;

import com.example.Artrental.novi.payload.mollie.Json.amount;
import com.example.Artrental.novi.payload.mollie.Json.metadata;

public class PaymentCreateRequest {
    private amount amount;
    private String description;
    private String redirectUrl;
    private String webhookUrl;
    private metadata metadata;

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

    public com.example.Artrental.novi.payload.mollie.Json.metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(com.example.Artrental.novi.payload.mollie.Json.metadata metadata) {
        this.metadata = metadata;
    }
}

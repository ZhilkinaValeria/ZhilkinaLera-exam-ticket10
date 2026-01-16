package ru.rksp.ZhilkinaLera.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentEventDto {

    @JsonProperty("фио_плательщика")
    private String payerFullName;

    @JsonProperty("сумма")
    private BigDecimal amount;

    @JsonProperty("валюта")
    private String currency;

    @JsonProperty("способ_оплаты")
    private String paymentMethod;

    @JsonProperty("дата_события")
    private LocalDateTime eventDate;

    // Конструктор по умолчанию (обязателен для Jackson)
    public PaymentEventDto() {
    }

    // Полный конструктор
    public PaymentEventDto(String payerFullName, BigDecimal amount, String currency,
                           String paymentMethod, LocalDateTime eventDate) {
        this.payerFullName = payerFullName;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.eventDate = eventDate;
    }

    // Геттеры и сеттеры
    public String getPayerFullName() {
        return payerFullName;
    }

    public void setPayerFullName(String payerFullName) {
        this.payerFullName = payerFullName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }
}
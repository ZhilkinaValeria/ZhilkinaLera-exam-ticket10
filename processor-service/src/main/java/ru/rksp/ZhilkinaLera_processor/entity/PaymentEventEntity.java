package ru.rksp.ZhilkinaLera.processor.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "сырые_события_платежей")
public class PaymentEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "идентификатор")
    private Long id;

    @Column(name = "фио_плательщика", nullable = false)
    private String payerFullName;

    @Column(name = "сумма", nullable = false)
    private BigDecimal amount;

    @Column(name = "валюта", nullable = false, length = 10)
    private String currency;

    @Column(name = "способ_оплаты", nullable = false, length = 50)
    private String paymentMethod;

    @Column(name = "дата_события", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "дата_создания")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Конструкторы
    public PaymentEventEntity() {}

    public PaymentEventEntity(String payerFullName, BigDecimal amount,
                              String currency, String paymentMethod, LocalDateTime eventDate) {
        this.payerFullName = payerFullName;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.eventDate = eventDate;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPayerFullName() { return payerFullName; }
    public void setPayerFullName(String payerFullName) { this.payerFullName = payerFullName; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public LocalDateTime getEventDate() { return eventDate; }
    public void setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
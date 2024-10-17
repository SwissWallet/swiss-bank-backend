package com.swiss.bank.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    private float value;

    private LocalDateTime datePurchase;

    @Enumerated(EnumType.STRING)
    private StatusPurhcase status = StatusPurhcase.PENDING;

    private String codePix = "noCode";

    public Purchase(Long id, UserEntity user, float value, LocalDateTime datePurchase, StatusPurhcase status, String codePix) {
        this.id = id;
        this.user = user;
        this.value = value;
        this.datePurchase = datePurchase;
        this.status = status;
        this.codePix = codePix;
    }

    public Purchase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public LocalDateTime getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(LocalDateTime datePurchase) {
        this.datePurchase = datePurchase;
    }

    public StatusPurhcase getStatus() {
        return status;
    }

    public void setStatus(StatusPurhcase status) {
        this.status = status;
    }

    public String getCodePix() {
        return codePix;
    }

    public void setCodePix(String codePix) {
        this.codePix = codePix;
    }
}

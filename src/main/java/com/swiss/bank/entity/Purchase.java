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
    @JoinColumn(name = "cardId", nullable = false)
    private Card card;

    private float value;

    private LocalDateTime datePurchase;

    private int parcel;

    public Purchase(Long id, Card card, float value, LocalDateTime datePurchase, int parcel) {
        this.id = id;
        this.card = card;
        this.value = value;
        this.datePurchase = datePurchase;
        this.parcel = parcel;
    }

    public Purchase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
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

    public int getParcel() {
        return parcel;
    }

    public void setParcel(int parcel) {
        this.parcel = parcel;
    }
}

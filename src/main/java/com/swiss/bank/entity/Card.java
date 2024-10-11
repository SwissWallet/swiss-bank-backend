package com.swiss.bank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)  // Renomeado para "id_user"
    private UserEntity user;

    private String cardNumber;

    private LocalDateTime validity;

    private String cvv;

    @Column(name = "card_limit")  // Renomeado para "card_limit"
    private float cardLimit;

    public Card(Long id, UserEntity user, String cardNumber, LocalDateTime validity, String cvv, float cardLimit) {
        this.id = id;
        this.user = user;
        this.cardNumber = cardNumber;
        this.validity = validity;
        this.cvv = cvv;
        this.cardLimit = cardLimit;
    }

    public Card() {
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDateTime getValidity() {
        return validity;
    }

    public void setValidity(LocalDateTime validity) {
        this.validity = validity;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public float getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(float cardLimit) {
        this.cardLimit = cardLimit;
    }
}

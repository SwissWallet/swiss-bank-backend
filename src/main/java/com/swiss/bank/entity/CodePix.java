package com.swiss.bank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "codesPix")
public class CodePix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float value;

    private String code;

    public CodePix(Long id, float value, String code) {
        this.id = id;
        this.value = value;
        this.code = code;
    }

    public CodePix() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

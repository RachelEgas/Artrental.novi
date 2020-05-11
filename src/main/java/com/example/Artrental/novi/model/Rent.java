package com.example.Artrental.novi.model;

import com.example.Artrental.novi.model.audit.UserDateAudit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.jetbrains.annotations.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "rent")
public class Rent extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Min(3)
    @Max(12)
    private int period;

    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "art_id")
    private Art art;

    @NotNull
    private Double total;

    private Boolean Payed;

    private String molliePaymentId;

    public Rent(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Art getArt() {
        return art;
    }

    public void setArt(Art art) {
        this.art = art;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getPayed() {
        return Payed;
    }

    public void setPayed(Boolean payed) {
        Payed = payed;
    }

    public String getMolliePaymentId() {
        return molliePaymentId;
    }

    public void setMolliePaymentId(String molliePaymentId) {
        this.molliePaymentId = molliePaymentId;
    }
}

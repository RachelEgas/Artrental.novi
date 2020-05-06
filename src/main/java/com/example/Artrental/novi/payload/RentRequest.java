package com.example.Artrental.novi.payload;

import com.example.Artrental.novi.model.Art;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RentRequest {
    @NotNull
    @Size(max = 12, min = 3)
    private int period;

    @NotNull
    private Art art;

    @NotNull
    private Double total;

    private Boolean payed;

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
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }
}

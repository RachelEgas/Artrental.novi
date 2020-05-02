package com.example.Artrental.novi.model;

import com.example.Artrental.novi.model.audit.UserDateAudit;
import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@Entity
@Table(name = "art")
public class Art extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotNull
    private Boolean inrental;

    @NotBlank
    @Size(max = 200)
    private String description;

    @NotNull
    private Double rentalprice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getInRental() {
        return inrental;
    }

    public void setInRental(Boolean inrental) {
        this.inrental = inrental;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRentalPrice() {
        return rentalprice;
    }

    public void setRentalPrice(Double rentalprice) {
        this.rentalprice = rentalprice;
    }

    public void setImage(String image) throws FileNotFoundException {

    }

    public String getImage(){
        return "";
    }
}

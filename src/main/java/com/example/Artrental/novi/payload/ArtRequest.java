package com.example.Artrental.novi.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArtRequest {
    @NotBlank
    @Size(min=5, max = 50)
    private String title;

    @NotNull
    private Boolean inRental;

    @NotBlank
    @Size(min=5, max = 200)
    private String description;

    @NotNull
    private Double rentalPrice;

    @NotNull
    private String artImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getInRental() {
        return inRental;
    }

    public void setInRental(Boolean inRental) {
        this.inRental = inRental;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(Double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getArtImage() {
        return artImage;
    }

    public void setArtImage(String artImage) {
        this.artImage = artImage;
    }
}

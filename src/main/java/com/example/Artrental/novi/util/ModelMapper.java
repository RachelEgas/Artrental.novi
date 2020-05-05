package com.example.Artrental.novi.util;

import com.example.Artrental.novi.model.Art;
import com.example.Artrental.novi.model.Rent;
import com.example.Artrental.novi.model.User;
import com.example.Artrental.novi.payload.ArtResponse;
import com.example.Artrental.novi.payload.RentResponse;
import com.example.Artrental.novi.payload.UserSummary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Base64;

public class ModelMapper {

    public static ArtResponse mapArtToArtResponse(Art art, User creator) {
        ArtResponse artResponse = new ArtResponse();
        artResponse.setId(art.getId());
        artResponse.setTitle(art.getTitle());
        artResponse.setDescription(art.getDescription());
        artResponse.setInRental(art.getInRental());
        artResponse.setRentalPrice(art.getRentalPrice());
        artResponse.setCreationDateTime(art.getCreatedAt());
        artResponse.setArtImage(readArtImageFromDirectory(art.getId()));
        Instant now = Instant.now();
        //artResponse.setExpired(art.getExpirationDateTime().isBefore(now));

        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getFullname());
        artResponse.setCreatedBy(creatorSummary);

        return artResponse;
    }

    public static RentResponse mapRentToRentResponse(Rent rent, User creator){
        RentResponse rentResponse = new RentResponse();
        rentResponse.setId(rent.getId());
        rentResponse.setCreationDateTime(Instant.now());

        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getFullname());
        rentResponse.setCreatedBy(creatorSummary);

        return rentResponse;
    }

    private static String readArtImageFromDirectory(long artId){
        String artImg = "";
        try {
            byte[] inputFile = Files.readAllBytes(Paths.get(AppConstants.ART_IMAGE_DIR+ artId+ ".jpg"));
            byte[] encodeBytes = Base64.getEncoder().encode(inputFile);
            artImg = new String(encodeBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return artImg;
    }
}

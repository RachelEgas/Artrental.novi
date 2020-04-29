package com.example.Artrental.novi.util;

import com.example.Artrental.novi.model.Art;
import com.example.Artrental.novi.model.User;
import com.example.Artrental.novi.payload.ArtResponse;
import com.example.Artrental.novi.payload.UserSummary;

import java.time.Instant;

public class ModelMapper {
    public static ArtResponse mapArtToArtResponse(Art art, User creator) {
        ArtResponse artResponse = new ArtResponse();
        artResponse.setId(art.getId());
        artResponse.setTitle(art.getTitle());
        artResponse.setDescription(art.getDescription());
        artResponse.setInRental(art.getInRental());
        artResponse.setRentalPrice(art.getRentalPrice());
        artResponse.setCreationDateTime(art.getCreatedAt());
        Instant now = Instant.now();
        //artResponse.setExpired(art.getExpirationDateTime().isBefore(now));

        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getFullname());
        artResponse.setCreatedBy(creatorSummary);

        return artResponse;
    }
}

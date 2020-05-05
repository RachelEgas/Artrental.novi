package com.example.Artrental.novi.service;
import com.example.Artrental.novi.exception.ResourceNotFoundException;
import com.example.Artrental.novi.model.Rent;
import com.example.Artrental.novi.model.User;
import com.example.Artrental.novi.payload.RentRequest;
import com.example.Artrental.novi.payload.RentResponse;
import com.example.Artrental.novi.repository.RentRepository;
import com.example.Artrental.novi.repository.UserRepository;
import com.example.Artrental.novi.security.UserPrincipal;
import com.example.Artrental.novi.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RentService {
    @Autowired
    RentRepository rentRepository;

    @Autowired
    UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ArtService.class);


    public Rent createRent(RentRequest rentRequest) {
        Rent rent = new Rent();
        rent.setArt(rentRequest.getArt());
        rent.setPeriod(rentRequest.getPeriod());
        rent.setTotal(rentRequest.getTotal());

        rent.setCreatedAt(Instant.now());
        return rent;
    }

    public RentResponse getRentById(Long rentId, UserPrincipal currentUser) {
        Rent rent = rentRepository.findById(rentId).orElseThrow(
                () -> new ResourceNotFoundException("Rent", "id", rentId));

        // Retrieve art creator details
        User creator = userRepository.findById(rent.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", rent.getCreatedBy()));

        return ModelMapper.mapRentToRentResponse(rent, creator);
    }
}

package com.example.Artrental.novi.service;
import com.example.Artrental.novi.exception.ResourceNotFoundException;
import com.example.Artrental.novi.model.Art;
import com.example.Artrental.novi.model.Rent;
import com.example.Artrental.novi.model.User;
import com.example.Artrental.novi.payload.RentRequest;
import com.example.Artrental.novi.payload.RentResponse;
import com.example.Artrental.novi.payload.mollie.PaymentCreateResponse;
import com.example.Artrental.novi.repository.ArtRepository;
import com.example.Artrental.novi.repository.RentRepository;
import com.example.Artrental.novi.repository.UserRepository;
import com.example.Artrental.novi.security.UserPrincipal;
import com.example.Artrental.novi.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Instant;

@Service
public class RentService {
    @Autowired
    RentRepository rentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArtRepository artRepository;

    @Autowired
    private PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(ArtService.class);

    public PaymentCreateResponse createRent(RentRequest rentRequest) throws MalformedURLException, URISyntaxException {
        Rent rent = new Rent();
        rent.setArt(rentRequest.getArt());
        rent.setPeriod(rentRequest.getPeriod());
        rent.setTotal(rentRequest.getTotal());
        rent.setPayed(rentRequest.getPayed());
        rent.setCreatedAt(Instant.now());

        ResponseEntity<PaymentCreateResponse> response = paymentService.createPayment(rent);

        rentRepository.save(rent);

        Art artToUpdate = rent.getArt();
        artToUpdate.setInRental(true);
        artRepository.save(artToUpdate);

        return response.getBody();
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

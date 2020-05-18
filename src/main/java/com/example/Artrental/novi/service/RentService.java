package com.example.Artrental.novi.service;
import com.example.Artrental.novi.exception.BadRequestException;
import com.example.Artrental.novi.exception.ResourceNotFoundException;
import com.example.Artrental.novi.model.Art;
import com.example.Artrental.novi.model.Rent;
import com.example.Artrental.novi.model.User;
import com.example.Artrental.novi.payload.ArtResponse;
import com.example.Artrental.novi.payload.PagedResponse;
import com.example.Artrental.novi.payload.RentRequest;
import com.example.Artrental.novi.payload.RentResponse;
import com.example.Artrental.novi.payload.mollie.PaymentCreateResponse;
import com.example.Artrental.novi.payload.mollie.PaymentStatusResponse;
import com.example.Artrental.novi.repository.ArtRepository;
import com.example.Artrental.novi.repository.RentRepository;
import com.example.Artrental.novi.repository.UserRepository;
import com.example.Artrental.novi.security.UserPrincipal;
import com.example.Artrental.novi.util.AppConstants;
import com.example.Artrental.novi.util.Helpers;
import com.example.Artrental.novi.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public PagedResponse<RentResponse> getRentCreatedBy(String username, UserPrincipal currentUser, int page, int size) {
        Helpers.validatePageNumberAndSize(page, size);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Retrieve all rent created by the given username
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Rent> rents = rentRepository.findByCreatedBy(user.getId(), pageable);

        if (rents.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), rents.getNumber(),
                    rents.getSize(), rents.getTotalElements(), rents.getTotalPages(), rents.isLast());
        }

        List<RentResponse> rentResponses = rents.map(rent -> {
            return ModelMapper.mapRentToRentResponse(rent,
                    user);
        }).getContent();

        return new PagedResponse<>(rentResponses, rents.getNumber(),
                rents.getSize(), rents.getTotalElements(), rents.getTotalPages(), rents.isLast());
    }

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

        // Retrieve rent creator details
        User creator = userRepository.findById(rent.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", rent.getCreatedBy()));

        return ModelMapper.mapRentToRentResponse(rent, creator);
    }

    public void GetPaymentAndSaveResponseToRent(String id){
        try {
            ResponseEntity<PaymentStatusResponse> result = paymentService.getMolliePayment(id);
            Optional<Rent> rent = rentRepository.findById(Long.parseLong(result.getBody().metadata.getOrder_id(), 10));
            if(rent.isPresent()){
                rent.get().setPayed(result.getBody().status == "payed");
                rentRepository.save(rent.get());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}

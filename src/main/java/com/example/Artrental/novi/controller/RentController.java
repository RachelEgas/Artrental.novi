package com.example.Artrental.novi.controller;

import com.example.Artrental.novi.model.Art;
import com.example.Artrental.novi.model.Rent;
import com.example.Artrental.novi.payload.ApiResponse;
import com.example.Artrental.novi.payload.ArtResponse;
import com.example.Artrental.novi.payload.RentRequest;
import com.example.Artrental.novi.payload.RentResponse;
import com.example.Artrental.novi.security.CurrentUser;
import com.example.Artrental.novi.security.UserPrincipal;
import com.example.Artrental.novi.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.example.Artrental.novi.util.AppConstants.TEST_API_MOLLIE_KEY;

@RestController
@RequestMapping("/api/rent")
public class RentController {
    @Autowired
    private RentService rentService;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRent(@Valid @RequestBody RentRequest rentRequest) {
        Rent rent = rentService.createRent(rentRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{rentId}")
                .buildAndExpand(rent.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Rental order Created Successfully"));
    }

    @GetMapping("/{rentId}")
    public RentResponse getRentById(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long rentId) {
        return rentService.getRentById(rentId, currentUser);
    }

    @GetMapping("/mollie_key")
    public String getMollieApiKey() {
        return TEST_API_MOLLIE_KEY;
    }
}

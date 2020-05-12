package com.example.Artrental.novi.controller;

import com.example.Artrental.novi.model.Art;
import com.example.Artrental.novi.model.Rent;
import com.example.Artrental.novi.payload.ApiResponse;
import com.example.Artrental.novi.payload.ArtResponse;
import com.example.Artrental.novi.payload.RentRequest;
import com.example.Artrental.novi.payload.RentResponse;
import com.example.Artrental.novi.payload.mollie.PaymentCreateResponse;
import com.example.Artrental.novi.security.CurrentUser;
import com.example.Artrental.novi.security.UserPrincipal;
import com.example.Artrental.novi.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.example.Artrental.novi.util.AppConstants.TEST_API_MOLLIE_KEY;

@RestController
@RequestMapping("/api/rent")
public class RentController {
    @Autowired
    private RentService rentService;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createRent(@Valid @RequestBody RentRequest rentRequest) {
        PaymentCreateResponse response = null;
        try {
            response = rentService.createRent(rentRequest);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{rentId}")
                .buildAndExpand(response.getMetadata().getOrder_id()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(
                        true,
                        "Rental order Created Successfully",
                        response.get_links().getCheckout().getHref()));
    }

    @GetMapping("/{rentId}")
    @PreAuthorize("hasRole('USER')")
    public RentResponse getRentById(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long rentId) {
        return rentService.getRentById(rentId, currentUser);
    }

    @GetMapping("/mollie_key")
    @PreAuthorize("hasRole('USER')")
    public String getMollieApiKey() {
        return TEST_API_MOLLIE_KEY;
    }
}

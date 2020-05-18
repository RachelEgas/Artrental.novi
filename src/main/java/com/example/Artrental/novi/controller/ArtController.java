package com.example.Artrental.novi.controller;

import com.example.Artrental.novi.model.Art;
import com.example.Artrental.novi.payload.ApiResponse;
import com.example.Artrental.novi.payload.ArtRequest;
import com.example.Artrental.novi.payload.ArtResponse;
import com.example.Artrental.novi.payload.PagedResponse;
import com.example.Artrental.novi.repository.ArtRepository;
import com.example.Artrental.novi.repository.UserRepository;
import com.example.Artrental.novi.security.CurrentUser;
import com.example.Artrental.novi.security.UserPrincipal;
import com.example.Artrental.novi.service.ArtService;
import com.example.Artrental.novi.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/art")
public class ArtController {

    @Autowired
    private ArtRepository artRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtService artService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public PagedResponse<ArtResponse> getArt(@CurrentUser UserPrincipal currentUser,
                                               @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return artService.getAllArt(currentUser, page, size);
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> createArt(@Valid @RequestBody ArtRequest artRequest) {
        Art art = artService.createArt(artRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{artId}")
                .buildAndExpand(art.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Art Created Successfully"));
    }

    @GetMapping("/{artId}")
    @PreAuthorize("hasRole('USER')")
    public ArtResponse getArtById(@CurrentUser UserPrincipal currentUser,
                                  @PathVariable Long artId) {
        return artService.getArtById(artId, currentUser);
    }
}

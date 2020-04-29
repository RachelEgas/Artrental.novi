package com.example.Artrental.novi.service;

import com.example.Artrental.novi.exception.BadRequestException;
import com.example.Artrental.novi.exception.ResourceNotFoundException;
import com.example.Artrental.novi.model.Art;
import com.example.Artrental.novi.model.User;
import com.example.Artrental.novi.payload.ArtRequest;
import com.example.Artrental.novi.payload.ArtResponse;
import com.example.Artrental.novi.payload.PagedResponse;
import com.example.Artrental.novi.repository.ArtRepository;
import com.example.Artrental.novi.repository.UserRepository;
import com.example.Artrental.novi.security.UserPrincipal;
import com.example.Artrental.novi.util.AppConstants;
import com.example.Artrental.novi.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArtService {

    @Autowired
    ArtRepository artRepository;

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ArtService.class);

    public PagedResponse<ArtResponse> getAllArt(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve art
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Art> arts = artRepository.findAll(pageable);

        if(arts.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), arts.getNumber(),
                    arts.getSize(), arts.getTotalElements(), arts.getTotalPages(), arts.isLast());
        }

        Map<Long, User> creatorMap = getArtCreatorMap(arts.getContent());

        List<ArtResponse> artResponses = arts.map(art -> {
            return ModelMapper.mapArtToArtResponse(art,
                    creatorMap.get(art.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(artResponses, arts.getNumber(),
                arts.getSize(), arts.getTotalElements(), arts.getTotalPages(), arts.isLast());
    }

    public PagedResponse<ArtResponse> getArtCreatedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Retrieve all art created by the given username
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Art> arts = artRepository.findByCreatedBy(user.getId(), pageable);

        if (arts.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), arts.getNumber(),
                    arts.getSize(), arts.getTotalElements(), arts.getTotalPages(), arts.isLast());
        }

        List<ArtResponse> artResponses = arts.map(art -> {
            return ModelMapper.mapArtToArtResponse(art,
                    user);
        }).getContent();

        return new PagedResponse<>(artResponses, arts.getNumber(),
                arts.getSize(), arts.getTotalElements(), arts.getTotalPages(), arts.isLast());
    }

    public Art createArt(ArtRequest artRequest) {
        Art art = new Art();
        art.setTitle(artRequest.getTitle());
        art.setInRental(artRequest.getInRental());
        art.setDescription(artRequest.getDescription());
        art.setRentalPrice(artRequest.getRentalPrice());
        art.setCreatedAt(Instant.now());

        return artRepository.save(art);
    }

    public ArtResponse getArtById(Long artId, UserPrincipal currentUser) {
        Art art = artRepository.findById(artId).orElseThrow(
                () -> new ResourceNotFoundException("Art", "id", artId));

        // Retrieve art creator details
        User creator = userRepository.findById(art.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", art.getCreatedBy()));

        return ModelMapper.mapArtToArtResponse(art, creator);
    }


    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    Map<Long, User> getArtCreatorMap(List<Art> arts) {
        // Get art Creator details of the given list of art
        List<Long> creatorIds = arts.stream()
                .map(Art::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }
}

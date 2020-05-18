package com.example.Artrental.novi.controller;

import com.example.Artrental.novi.exception.ResourceNotFoundException;
import com.example.Artrental.novi.model.User;
import com.example.Artrental.novi.payload.*;
import com.example.Artrental.novi.repository.ArtRepository;
import com.example.Artrental.novi.repository.RentRepository;
import com.example.Artrental.novi.repository.UserRepository;
import com.example.Artrental.novi.security.UserPrincipal;
import com.example.Artrental.novi.security.CurrentUser;
import com.example.Artrental.novi.service.ArtService;
import com.example.Artrental.novi.service.RentService;
import com.example.Artrental.novi.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtRepository artRepository;

    @Autowired
    private ArtService artService;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private RentService rentService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(), user.getRoles());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    @PreAuthorize("hasRole('USER')")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        UserProfile userProfile = new UserProfile(
                user.getId(),
                user.getUsername(),
                user.getFullname(),
                user.getCreatedAt(),
                artRepository.countByCreatedBy(user.getId()),
                rentRepository.countByCreatedBy(user.getId()));

        return userProfile;
    }

    @GetMapping("/users/{username}/art")
    @PreAuthorize("hasRole('STAFF')")
    public PagedResponse<ArtResponse> getArtCreatedBy(@PathVariable(value = "username") String username,
                                                      @CurrentUser UserPrincipal currentUser,
                                                      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return artService.getArtCreatedBy(username, currentUser, page, size);
    }

    @GetMapping("/users/{username}/rent")
    @PreAuthorize("hasRole('USER')")
    public PagedResponse<RentResponse> getRentCreatedBy(@PathVariable(value = "username")String username,
                                                        @CurrentUser UserPrincipal currentUser,
                                                        @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
        return rentService.getRentCreatedBy(username, currentUser, page, size);
    }
}

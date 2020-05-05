package com.example.Artrental.novi.repository;

import com.example.Artrental.novi.model.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, Long> {

    Optional<Rent> findById(long rentId);

    Page<Rent> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(long userId);
}
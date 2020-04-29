package com.example.Artrental.novi.repository;

import com.example.Artrental.novi.model.Art;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtRepository extends JpaRepository<Art, Long> {

    Optional<Art> findById(long artId);

    Page<Art> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(long userId);
}

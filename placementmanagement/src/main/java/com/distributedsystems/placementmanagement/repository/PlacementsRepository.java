package com.distributedsystems.placementmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.distributedsystems.placementmanagement.entity.Placements;

@Repository
public interface PlacementsRepository extends JpaRepository<Placements, Long>{
    @Query("select c from Placements as c where c.companyName = :companyName")
    Optional<Placements> findByCompanyName(String companyName);
}

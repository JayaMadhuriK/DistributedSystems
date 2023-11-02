package com.distributedsystems.placementmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.distributedsystems.placementmanagement.entity.Placements;

@Repository
public interface PlacementsRepository extends JpaRepository<Placements, Long>{

}

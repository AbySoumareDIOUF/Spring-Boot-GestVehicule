package com.tp.vehicule.repository;

import com.tp.vehicule.model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule,Long> {
    Vehicule findByMatricule(String matricule);
    List<Vehicule> findByModeleId(Long modeleId);
}

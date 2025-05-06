package com.tp.vehicule.repository;

import com.tp.vehicule.model.Modele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeleRepository extends JpaRepository<Modele,Long> {
}

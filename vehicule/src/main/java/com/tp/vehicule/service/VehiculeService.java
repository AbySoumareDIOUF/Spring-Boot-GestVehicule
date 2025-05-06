package com.tp.vehicule.service;

import com.tp.vehicule.model.Vehicule;
import com.tp.vehicule.repository.VehiculeRepository;
import com.tp.vehicule.EncryptDecrypt.EncryptorDecryptor;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class VehiculeService {

    private static final Logger logger = LoggerFactory.getLogger(VehiculeService.class);

    private final VehiculeRepository vehiculeRepository;

    public VehiculeService(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    // Enregistrer un véhicule
    public Vehicule saveVehicule(Vehicule vehicule){
        try {
            logger.info("Enregistrement du véhicule avec matricule : {}", vehicule.getMatricule());
            Vehicule savedVehicule = vehiculeRepository.save(vehicule);
            logger.info("Véhicule enregistré avec succès : {}", savedVehicule.getId());
            return savedVehicule;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur d'intégrité des données lors de l'enregistrement du véhicule", e);
            throw new RuntimeException("Erreur d'intégrité des données lors de l'enregistrement du véhicule", e);
        }
    }

    // Lister tous les véhicules
    public List<Vehicule> findAllVehicule(){
        try {
            logger.info("Récupération de tous les véhicules");
            return vehiculeRepository.findAll();
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des véhicules", e);
            throw new RuntimeException("Erreur lors de la récupération des véhicules", e);
        }
    }

    // Recherche par matricule (chiffrement déjà géré dans le mapper)
    public Vehicule findByMatricule(String matricule){
        try {
            logger.info("Recherche du véhicule avec matricule : {}", matricule);
            Vehicule vehicule = vehiculeRepository.findByMatricule(matricule);
            if (vehicule == null) {
                logger.warn("Aucun véhicule trouvé avec le matricule : {}", matricule);
            } else {
                logger.info("Véhicule trouvé : {}", vehicule.getId());
            }
            return vehicule;
        } catch (Exception e) {
            logger.error("Erreur lors de la recherche du véhicule par matricule", e);
            throw new RuntimeException("Erreur lors de la recherche du véhicule par matricule", e);
        }
    }

    // Recherche des véhicules ayant le même modèle
    public List<Vehicule> findByModeleId(Long id){
        try {
            logger.info("Recherche des véhicules pour le modèle avec ID : {}", id);
            return vehiculeRepository.findByModeleId(id);
        } catch (Exception e) {
            logger.error("Erreur lors de la recherche des véhicules par modèle", e);
            throw new RuntimeException("Erreur lors de la recherche des véhicules par modèle", e);
        }
    }
}

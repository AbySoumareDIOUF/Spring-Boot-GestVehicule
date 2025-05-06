package com.tp.vehicule.helper;

import com.tp.vehicule.EncryptDecrypt.EncryptorDecryptor;
import com.tp.vehicule.dto.VehiculeDto;
import com.tp.vehicule.dto.VehiculeRequest;
import com.tp.vehicule.mapper.VehiculeMapper;
import com.tp.vehicule.model.Vehicule;
import com.tp.vehicule.service.VehiculeService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VehiculeHelper {

    public final VehiculeMapper vehiculeMapper;
    public final VehiculeService vehiculeService;

    public VehiculeHelper(VehiculeMapper vehiculeMapper, VehiculeService vehiculeService) {
        this.vehiculeMapper = vehiculeMapper;
        this.vehiculeService = vehiculeService;
    }

    // Méthode pour enregistrer un véhicule avec chiffrement de la matricule
    public VehiculeDto saveVehicule(VehiculeRequest vehiculeRequest) throws Exception {
        // Vérifie que le numéro de châssis est positif
        if (vehiculeRequest.getNumChassis() == null || vehiculeRequest.getNumChassis() <= 0) {
            throw new Exception("Le numero de chassis doit etre positif");
        }

        // Vérifie que la matricule n'existe pas déjà (en chiffrant avant la recherche)
        if (vehiculeService.findByMatricule(vehiculeRequest.getMatricule()) != null) {
            throw new Exception("Un vehicule avec la matricule " + vehiculeRequest.getMatricule() + " existe déjà !");
        }

        // Convertir VehiculeRequest en Vehicule, où la matricule est déjà chiffrée
        Vehicule vehicule = vehiculeMapper.toVehicule(vehiculeRequest);

        // Sauvegarde dans la base et retour du DTO
        return vehiculeMapper.toVehiculeDto(
                vehiculeService.saveVehicule(vehicule)
        );
    }

    // Méthode pour récupérer tous les véhicules existants
    public List<VehiculeDto> findAllVehicule() {
        return vehiculeService.findAllVehicule().stream()
                .map(vehicule -> {
                    // Déchiffrer la matricule pour l'affichage
                    try {
                        vehicule.setMatricule(EncryptorDecryptor.decrypt(vehicule.getMatricule()));
                    } catch (Exception e) {
                        throw new RuntimeException("Erreur de déchiffrement de la matricule", e);
                    }
                    return vehiculeMapper.toVehiculeDto(vehicule);
                })
                .toList();
    }
}

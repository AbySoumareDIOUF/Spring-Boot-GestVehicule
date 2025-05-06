package com.tp.vehicule.mapper;

import com.tp.vehicule.dto.ModeleDto;
import com.tp.vehicule.dto.VehiculeDto;
import com.tp.vehicule.dto.VehiculeRequest;
import com.tp.vehicule.EncryptDecrypt.EncryptorDecryptor;
import com.tp.vehicule.model.Modele;
import com.tp.vehicule.model.Vehicule;
import org.springframework.stereotype.Component;

// Mapper pour la conversion entre les entités et DTOs
@Component
public class VehiculeMapper {

    // Convertit un objet Vehicule en VehiculeDto (en déchiffrant la matricule)
    public VehiculeDto toVehiculeDto(Vehicule vehicule) {
        // Log pour vérifier si le déchiffrement fonctionne
        System.out.println("Déchiffrement de la matricule : " + vehicule.getMatricule());

        return VehiculeDto.builder()
                .id(vehicule.getId())
                .matricule(decryptMatricule(vehicule.getMatricule()))  // Déchiffre la matricule pour l'affichage
                .couleur(vehicule.getCouleur())
                .numChassis(vehicule.getNumChassis())
                .annee(vehicule.getAnnee())
                .modele(toModeleDto(vehicule.getModele()))
                .build();
    }

    // Convertit un objet Modele en ModeleDto
    public ModeleDto toModeleDto(Modele modele) {
        return ModeleDto.builder()
                .id(modele.getId())
                .nom(modele.getNom())
                .build();
    }

    // Convertit un VehiculeRequest en Vehicule en chiffrant la matricule
    public Vehicule toVehicule(VehiculeRequest vehiculeRequest) {
        // Log pour vérifier si le chiffrement fonctionne
        System.out.println("Chiffrement de la matricule : " + vehiculeRequest.getMatricule());

        return Vehicule.builder()
                .matricule(encryptMatricule(vehiculeRequest.getMatricule()))  // Chiffre la matricule
                .couleur(vehiculeRequest.getCouleur())
                .numChassis(vehiculeRequest.getNumChassis())
                .annee(vehiculeRequest.getAnnee())
                .modele(Modele.builder().id(vehiculeRequest.getModeleId()).build())  // Associe le modèle via son ID
                .build();
    }

    // Méthode pour chiffrer la matricule
    private String encryptMatricule(String matricule) {
        try {
            // Chiffrement de la matricule via EncryptorDecryptor
            String encryptedMatricule = EncryptorDecryptor.encrypt(matricule);
            // Log pour vérifier le résultat du chiffrement
            System.out.println("Matricule chiffré : " + encryptedMatricule);
            return encryptedMatricule;
        } catch (Exception e) {
            throw new ChiffrementException("Erreur lors du chiffrement de la matricule", e); // Exception personnalisée
        }
    }

    // Méthode pour déchiffrer la matricule
    private String decryptMatricule(String matriculeChiffree) {
        try {
            // Déchiffrement de la matricule via EncryptorDecryptor
            String decryptedMatricule = EncryptorDecryptor.decrypt(matriculeChiffree);
            // Log pour vérifier le résultat du déchiffrement
            System.out.println("Matricule déchiffré : " + decryptedMatricule);
            return decryptedMatricule;
        } catch (Exception e) {
            throw new ChiffrementException("Erreur lors du déchiffrement de la matricule", e); // Exception personnalisée
        }
    }
}

// Exception personnalisée pour le chiffrement/déchiffrement
class ChiffrementException extends RuntimeException {
    public ChiffrementException(String message, Throwable cause) {
        super(message, cause);
    }
}

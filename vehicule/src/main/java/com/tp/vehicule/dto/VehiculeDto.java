package com.tp.vehicule.dto;

import com.tp.vehicule.EncryptDecrypt.EncryptorDecryptor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehiculeDto {
    private Long id;
    private String matricule;
    private String couleur;
    private Integer numChassis;
    private Integer annee;
    private ModeleDto modele;

    // Déchiffrement du matricule lors de l'accès
    public String getMatricule() {
        try {
            // Si le matricule est chiffré, on le déchiffre pour le rendre lisible
            return matricule != null ? EncryptorDecryptor.decrypt(matricule) : null;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du déchiffrement du matricule", e);
        }
    }

    // Chiffrement du matricule avant de l'envoyer dans la base de données
    public void setMatricule(String matricule) {
        try {
            // Chiffrement du matricule avant de le sauvegarder
            this.matricule = matricule != null ? EncryptorDecryptor.encrypt(matricule) : null;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chiffrement du matricule", e);
        }
    }
}

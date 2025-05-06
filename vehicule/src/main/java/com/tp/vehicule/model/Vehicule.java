package com.tp.vehicule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.tp.vehicule.EncryptDecrypt.EncryptorDecryptor;  // Importer la classe EncryptorDecryptor

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 500) // Ajuste la longueur si nécessaire
    private String matricule;  // Ce champ sera crypté dans la base de données

    @Column(length = 50)
    private String couleur;

    private Integer numChassis;
    private Integer annee;

    @ManyToOne
    @JoinColumn(name = "modele_id", nullable = false)
    private Modele modele;

    // Getter pour 'matricule' (déchiffrement)
    public String getMatricule() {
        try {
            return EncryptorDecryptor.decrypt(this.matricule); // Décryptage du matricule
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du déchiffrement du matricule", e);
        }
    }

    // Setter pour 'matricule' (chiffrement)
    public void setMatricule(String matricule) {
        try {
            this.matricule = EncryptorDecryptor.encrypt(matricule); // Chiffrement du matricule
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chiffrement du matricule", e);
        }
    }
}

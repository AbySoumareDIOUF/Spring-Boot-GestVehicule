package com.tp.vehicule.service;

import com.tp.vehicule.model.Modele;
import com.tp.vehicule.repository.ModeleRepository;
import org.springframework.stereotype.Service;

@Service
public class ModeleService {

    private final ModeleRepository modeleRepository;

    public ModeleService(ModeleRepository modeleRepository) {
        this.modeleRepository = modeleRepository;
    }

    public Modele saveModele(Modele modele) {
        return modeleRepository.save(modele);
    }
}

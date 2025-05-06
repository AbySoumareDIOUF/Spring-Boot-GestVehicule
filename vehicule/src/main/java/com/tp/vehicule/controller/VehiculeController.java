package com.tp.vehicule.controller;

import com.tp.vehicule.dto.VehiculeDto;
import com.tp.vehicule.dto.VehiculeRequest;
import com.tp.vehicule.helper.VehiculeHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vehicules")
@Validated
public class VehiculeController {

    private final VehiculeHelper vehiculeHelper;

    // Injection du helper via le constructeur
    public VehiculeController(VehiculeHelper vehiculeHelper) {
        this.vehiculeHelper = vehiculeHelper;
    }

    /**
     * Endpoint pour récupérer tous les véhicules
     * GET /vehicules
     */
    @GetMapping
    public List<VehiculeDto> allVehicule() {
        return vehiculeHelper.findAllVehicule();
    }

    /**
     * Endpoint pour créer un nouveau véhicule
     * POST /vehicules
     */
    @PostMapping
    public ResponseEntity<VehiculeDto> saveVehicule(@RequestBody @Valid VehiculeRequest vehiculeRequest) throws Exception {
        // Assurez-vous que le matricule est bien chiffré avant de le sauvegarder
        if (vehiculeRequest.getMatricule() == null || vehiculeRequest.getMatricule().isEmpty()) {
            return ResponseEntity.badRequest().body(null);  // Matricule obligatoire
        }

        // Créez et renvoyez le véhicule
        VehiculeDto savedVehicule = vehiculeHelper.saveVehicule(vehiculeRequest);

        // Retourner une réponse avec le statut 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicule);
    }
}

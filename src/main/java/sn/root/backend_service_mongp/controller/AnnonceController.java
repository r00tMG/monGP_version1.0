package sn.root.backend_service_mongp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.root.backend_service_mongp.dto.AnnonceRequestDto;
import sn.root.backend_service_mongp.entities.Annonce;
import sn.root.backend_service_mongp.entities.User;
import sn.root.backend_service_mongp.mapper.AnnonceMapper;
import sn.root.backend_service_mongp.service.AnnonceService;
import sn.root.backend_service_mongp.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/annonces")
@Tag(name = "Gestion des annonces")
public class AnnonceController {
    private final AnnonceService annonceService;
    private final AnnonceMapper annonceMapper;

    public AnnonceController(AnnonceService annonceService, AnnonceMapper annonceMapper) {
        this.annonceService = annonceService;
        this.annonceMapper = annonceMapper;
    }

    @GetMapping
    @Operation(summary = "Récupération des annonces", description = "Récupération de la liste des annonces")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<List<Annonce>> index(){
        List<Annonce> allAnnonce = annonceService.getAllAnnonce();
        return new ResponseEntity<>(allAnnonce, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupération d'un annnonce", description = "Récupération des détails d'un annnonce")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<Optional<Annonce>> show(@PathVariable("id") Long id){
        Optional<Annonce> annonce = annonceService.getAnnonceById(id);
        return new ResponseEntity<>(annonce, HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = "Création d'un annonce", description = "Création d'un annonce dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<Annonce> create(@RequestBody AnnonceRequestDto annonceRequestDto){
        Annonce annonce = annonceMapper.fromAnnonce(annonceRequestDto);
        Annonce annonceSaved = annonceService.saveAnnonce(annonce);
        return new ResponseEntity<>(annonceSaved, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Suppression d'une annonce", description = "Suppression d'une annonce dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        annonceService.deleteAnnonce(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update d'un utilisateur", description = "Update d'un utilisateur dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<Annonce> update(@PathVariable("id") Long id, @RequestBody Annonce annonce){
        Optional<Annonce> annonceById = annonceService.getAnnonceById(id);
        if (annonceById.isPresent())
        {
            Annonce annonceToUpdated = annonceById.get();
            annonceToUpdated.setDateArrivee(annonce.getDateArrivee());
            annonceToUpdated.setDateDepart(annonce.getDateDepart());
            annonceToUpdated.setDestination(annonce.getDestination());
            annonceToUpdated.setOrigin(annonce.getOrigin());
            annonceToUpdated.setUser(annonce.getUser());
            Annonce annonceUpdated = annonceService.saveAnnonce(annonceToUpdated);
            return new ResponseEntity<>(annonceUpdated, HttpStatus.OK);
        }else {
            throw new RuntimeException("Error id:" + id + "not found");
        }

    }

}

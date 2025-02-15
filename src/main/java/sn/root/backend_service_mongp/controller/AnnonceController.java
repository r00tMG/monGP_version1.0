package sn.root.backend_service_mongp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.root.backend_service_mongp.dto.AnnonceRequestDto;
import sn.root.backend_service_mongp.entities.Annonce;
import sn.root.backend_service_mongp.entities.User;
import sn.root.backend_service_mongp.mapper.EntityMapper;
import sn.root.backend_service_mongp.repository.UserRepository;
import sn.root.backend_service_mongp.service.AnnonceService;
import sn.root.backend_service_mongp.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/annonces")
@Tag(name = "Gestion des annonces")
public class AnnonceController {
    private final AnnonceService annonceService;
    private final EntityMapper entityMapper;
    private final UserService userService;
    private ModelMapper modelMapper = new ModelMapper();

    public AnnonceController(AnnonceService annonceService, EntityMapper entityMapper, UserService userService) {
        this.annonceService = annonceService;
        this.entityMapper = entityMapper;
        this.userService = userService;
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
        Annonce annonce = entityMapper.fromAnnonce(annonceRequestDto);
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
    public ResponseEntity<Annonce> update(@PathVariable("id") Long id, @RequestBody AnnonceRequestDto annonceRequestDto){
        Annonce annonceToUpdate = annonceService.getAnnonceById(id).get();
        if(id == null) {
            throw new RuntimeException("Error id:" + id + "not found");
        }
        modelMapper.map(annonceRequestDto, annonceToUpdate);
        if(annonceRequestDto.getUser_id() != null){
            //User userById = userRepository.findById(annonceRequestDto.getUser_id()).get();
            User userById = userService.getUserById(annonceRequestDto.getUser_id()).get();
            annonceToUpdate.setUser(userById);
        }
        Annonce annonceUpdated = annonceService.saveAnnonce(annonceToUpdate);
        return new ResponseEntity<>(annonceUpdated, HttpStatus.OK);


    }

    @GetMapping("depart/mc")
    @Operation(summary = "Filtrer par date de départ", description = "Rechercher par date depart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error server")
    })
    public ResponseEntity<List<Annonce>> indexByDepart(@PathVariable(name = "mc") LocalDateTime mc){
        List<Annonce> annonceByDepart = annonceService.getAnnonceByDepart(mc);
        return new ResponseEntity<>(annonceByDepart, HttpStatus.OK);
    }

    @GetMapping("arrivee/mc")
    @Operation(summary = "Filtrer par date d'arrivée", description = "Rechercher par date d'arrivée")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error server")
    })
    public ResponseEntity<List<Annonce>> indexByArrivee(@PathVariable(name = "mc") LocalDateTime mc){
        List<Annonce> annonceByArrive = annonceService.getAnnonceByArrive(mc);
        return new ResponseEntity<>(annonceByArrive, HttpStatus.OK);
    }

    @GetMapping("destination/mc")
    @Operation(summary = "Filtrer par destination", description = "Rechercher par destination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error server")
    })
    public ResponseEntity<List<Annonce>> indexByDestination(@PathVariable(name = "mc") String mc){
        List<Annonce> annonceByDestination = annonceService.getAnnonceByDestination(mc);
        return new ResponseEntity<>(annonceByDestination, HttpStatus.OK);
    }

    @GetMapping("origine/mc")
    @Operation(summary = "Filtrer par origine", description = "Rechercher par origine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error server")
    })
    public ResponseEntity<List<Annonce>> indexByOrigine(@PathVariable(name = "mc") String mc){
        List<Annonce> annonceByDestination = annonceService.getAnnonceByOrigine(mc);
        return new ResponseEntity<>(annonceByDestination, HttpStatus.OK);
    }

}

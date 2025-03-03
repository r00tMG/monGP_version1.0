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
import sn.root.backend_service_mongp.dto.ReservationRequestDto;
import sn.root.backend_service_mongp.entities.Annonce;
import sn.root.backend_service_mongp.entities.Reservation;
import sn.root.backend_service_mongp.entities.User;
import sn.root.backend_service_mongp.enumeration.StatusReservation;
import sn.root.backend_service_mongp.mapper.EntityMapper;
import sn.root.backend_service_mongp.repository.AnnonceRepository;
import sn.root.backend_service_mongp.repository.ReservationRepository;
import sn.root.backend_service_mongp.repository.UserRepository;
import sn.root.backend_service_mongp.service.AnnonceService;
import sn.root.backend_service_mongp.service.ReservationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Gestion des réservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final EntityMapper entityMapper;
    private ModelMapper modelMapper = new ModelMapper();
    private final AnnonceRepository annonceRepository;
    private final UserRepository userRepository;
    private final AnnonceService annonceService;

    public ReservationController(ReservationService reservationService, EntityMapper entityMapper, AnnonceRepository annonceRepository, UserRepository userRepository, ReservationRepository reservationRepository, AnnonceService annonceService) {
        this.reservationService = reservationService;
        this.entityMapper = entityMapper;
        this.annonceRepository = annonceRepository;
        this.userRepository = userRepository;
        this.annonceService = annonceService;
    }


    @GetMapping
    @Operation(summary = "Récupération des reservation", description = "Récupération de la liste des reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<List<Reservation>> index() {
        List<Reservation> allReservation = reservationService.getAllReservation();
        return new ResponseEntity<>(allReservation, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupération d'une reservation", description = "Récupération des détails d'une reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<Optional<Reservation>> show(@PathVariable("id") Long id) {
        Optional<Reservation> reservationById = reservationService.getReservationById(id);
        return new ResponseEntity<>(reservationById, HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = "Création d'une reservation", description = "Création d'une reservation dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation reservation = entityMapper.fromReservation(reservationRequestDto);

        // Kilo disponible sur l'annonce à réserver
        Integer kilosDisponibles = reservation.getAnnonce().getKilosDisponibles();
        System.out.println("Kilo disponible" + kilosDisponibles);

        // Kilo réservé sur l'annonce à réserver
        Integer kilosReserved = reservation.getKilos_demandes();
        System.out.println("Kilo à réserver" + kilosReserved);

        if(kilosDisponibles >= kilosReserved)
        {
            // Kilo restante sur l'annonce à réservé
            Integer kiloRestante = kilosDisponibles - kilosReserved;

            // Prix du kilo
            Double prixDuKilo = reservation.getAnnonce().getPrixDuKilo();

            // Prix de la demande
            double prixDelaDemande = prixDuKilo * kilosReserved;
            System.out.println("Prix de la reservation :" + prixDelaDemande);

            //Enregistrement du status de la réservation
            reservation.setStatus(StatusReservation.RESERVED);
            System.out.println("Enregistrement du status de la réservation");

            //Enregistrement du prix de la demande
            reservation.setPrix_de_la_demande(prixDelaDemande);
            System.out.println("Enregistrement du prix de la demande");

            // Enregistrement de la reservation dans la base de données
            reservationService.saveReservation(reservation);
            System.out.println("Enregistrement de la reservation dans la base de données");

            // Modification du kilo disponible de l'annonce réservée
            Annonce annonceReserved = annonceService.getAnnonceById(reservationRequestDto.getAnnonce_id()).get();
            annonceReserved.setKilosDisponibles(kiloRestante);
            System.out.println("Modification du kilo disponible de l'annonce réservée");

        }else{
            throw new RuntimeException("Le nombre de kilo disponible:" + kilosDisponibles +
                    " ne doit pas être inférieur au nombre de kilo resérvée: " + kilosReserved);
        }


        Reservation reservationSaved = reservationService.saveReservation(reservation);
        return new ResponseEntity<>(reservationSaved, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Suppression d'une reservation", description = "Suppression d'une reservation dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update d'un utilisateur", description = "Update d'un utilisateur dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<Reservation> update(@PathVariable("id") Long id, @RequestBody ReservationRequestDto reservationRequestDto) {
       Reservation reservationToUpdate = reservationService.getReservationById(id).get();
        if (id == null) {
            throw new RuntimeException("Error id:" + id + "not found");
        }
        modelMapper.map(reservationRequestDto, reservationToUpdate);
        if (reservationRequestDto.getAnnonce_id() != null) {
            Annonce annonceById = annonceRepository.findById(reservationRequestDto.getAnnonce_id()).get();
            reservationToUpdate.setAnnonce(annonceById);
        }
        if (reservationRequestDto.getUser_id() != null) {
            User userById = userRepository.findById(reservationRequestDto.getUser_id()).get();
            reservationToUpdate.setUser(userById);
        }

        Reservation reservation = reservationService.saveReservation(reservationToUpdate);
        return new ResponseEntity<>(reservation, HttpStatus.OK);


    }


}

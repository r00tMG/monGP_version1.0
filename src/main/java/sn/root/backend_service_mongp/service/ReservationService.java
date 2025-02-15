package sn.root.backend_service_mongp.service;

import org.springframework.stereotype.Service;
import sn.root.backend_service_mongp.entities.Annonce;
import sn.root.backend_service_mongp.entities.Reservation;
import sn.root.backend_service_mongp.repository.AnnonceRepository;
import sn.root.backend_service_mongp.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    public final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    public List<Reservation> getAllReservation(){
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id){
        return reservationRepository.findById(id);
    }

    public void deleteReservation(Long id){
        reservationRepository.deleteById(id);
    }

    public Reservation saveReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }


}

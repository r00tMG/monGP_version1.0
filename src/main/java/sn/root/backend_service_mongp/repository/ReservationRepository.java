package sn.root.backend_service_mongp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.root.backend_service_mongp.entities.Annonce;
import sn.root.backend_service_mongp.entities.Reservation;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

}

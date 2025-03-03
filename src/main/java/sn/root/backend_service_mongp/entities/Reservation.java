package sn.root.backend_service_mongp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.root.backend_service_mongp.enumeration.StatusReservation;

@Entity @Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer kilos_demandes;
    private Double  prix_de_la_demande;
    @Enumerated(EnumType.STRING)
    private StatusReservation status = StatusReservation.PENDING;

    @ManyToOne
    @JoinColumn(name = "annonce_id", nullable = false)
    private Annonce annonce;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    private void setDefaultStatus() {
        if (status == null) {
            status = StatusReservation.PENDING;
        }
    }

}

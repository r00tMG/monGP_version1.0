package sn.root.backend_service_mongp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import sn.root.backend_service_mongp.enumeration.StatusReservation;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ReservationRequestDto {
    private Integer kilos_demandes;
    private Double  prix_de_la_demande;
    private StatusReservation status;
    private Long annonce_id;
    private Long user_id;
}

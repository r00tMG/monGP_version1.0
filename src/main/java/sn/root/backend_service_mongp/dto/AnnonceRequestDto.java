package sn.root.backend_service_mongp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class AnnonceRequestDto {
    private Integer kilosDisponibles;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrivee;
    private String description;
    private Double prixDuKilo;
    private String origin;
    private String destination;
    private Long user_id;
}

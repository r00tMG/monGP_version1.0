package sn.root.backend_service_mongp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Annonce implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer kilosDisponibles;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrivee;
    private String description;
    private Double prixDuKilo;
    private String origin;
    private String destination;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

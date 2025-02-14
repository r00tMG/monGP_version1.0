package sn.root.backend_service_mongp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.root.backend_service_mongp.entities.Annonce;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce,Long> {
    List<Annonce> findByDateDepart(LocalDateTime mc);
    List<Annonce> findByDateArrivee(LocalDateTime mc);
    List<Annonce> findByDestination(String mc);
    List<Annonce> findByOrigin(String mc);
    List<Annonce> findByUser_Email(String mc);
}

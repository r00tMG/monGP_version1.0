package sn.root.backend_service_mongp.service;

import org.springframework.stereotype.Service;
import sn.root.backend_service_mongp.entities.Annonce;
import sn.root.backend_service_mongp.entities.User;
import sn.root.backend_service_mongp.repository.AnnonceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnnonceService {
    public final AnnonceRepository annonceRepository;

    public AnnonceService(AnnonceRepository annonceRepository) {
        this.annonceRepository = annonceRepository;
    }

    public List<Annonce> getAllAnnonce(){
        return annonceRepository.findAll();
    }

    public Optional<Annonce> getAnnonceById(Long id){
        return annonceRepository.findById(id);
    }

    public void deleteAnnonce(Long id){
        annonceRepository.deleteById(id);
    }

    public Annonce saveAnnonce(Annonce annonce){
        return annonceRepository.save(annonce);
    }

    public List<Annonce> getAnnonceByDepart(LocalDateTime mc){
        return annonceRepository.findByDateDepart(mc);
    }

    public List<Annonce> getAnnonceByArrive(LocalDateTime mc){
        return annonceRepository.findByDateArrivee(mc);
    }

    public List<Annonce> getAnnonceByOrigine(String mc){
        return annonceRepository.findByOrigin(mc);
    }

    public List<Annonce> getAnnonceByDestination(String mc){
        return annonceRepository.findByDestination(mc);
    }

    public List<Annonce> getAnnonceByUser(String mc){
        return annonceRepository.findByUser_Email(mc);
    }
}

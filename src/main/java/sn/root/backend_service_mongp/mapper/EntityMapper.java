package sn.root.backend_service_mongp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sn.root.backend_service_mongp.dto.AnnonceRequestDto;
import sn.root.backend_service_mongp.dto.ReservationRequestDto;
import sn.root.backend_service_mongp.entities.Annonce;
import sn.root.backend_service_mongp.entities.Reservation;
import sn.root.backend_service_mongp.entities.User;
import sn.root.backend_service_mongp.repository.AnnonceRepository;
import sn.root.backend_service_mongp.repository.UserRepository;

import java.util.Optional;

@Component
public class EntityMapper {
    private ModelMapper modelMapper = new org.modelmapper.ModelMapper();
    private final UserRepository userRepository;
    private final AnnonceRepository annonceRepository;

    public EntityMapper(UserRepository userRepository, AnnonceRepository annonceRepository) {
        this.userRepository = userRepository;
        this.annonceRepository = annonceRepository;
    }

    public Annonce fromAnnonce(AnnonceRequestDto annonceRequestDto){
        Annonce annonce = modelMapper.map(annonceRequestDto, Annonce.class);
        User userById = userRepository.findById(annonceRequestDto.getUser_id()).get();
        annonce.setUser(userById);
        return annonce;
    }

    public Reservation fromReservation(ReservationRequestDto reservationRequestDto){
        Reservation reservation = modelMapper.map(reservationRequestDto, Reservation.class);
        Annonce annonceById = annonceRepository.findById(reservationRequestDto.getAnnonce_id()).get();
        User userById = userRepository.findById(reservationRequestDto.getUser_id()).get();
        reservation.setAnnonce(annonceById);
        reservation.setUser(userById);
        return reservation;
    }

}

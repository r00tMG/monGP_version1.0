package sn.root.backend_service_mongp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sn.root.backend_service_mongp.dto.AnnonceRequestDto;
import sn.root.backend_service_mongp.entities.Annonce;
import sn.root.backend_service_mongp.entities.User;
import sn.root.backend_service_mongp.repository.UserRepository;

import java.util.Optional;

@Component
public class AnnonceMapper {
    private ModelMapper modelMapper = new ModelMapper();
    private final UserRepository userRepository;

    public AnnonceMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Annonce fromAnnonce(AnnonceRequestDto annonceRequestDto){
        Annonce annonce = modelMapper.map(annonceRequestDto, Annonce.class);
        User userById = userRepository.findById(annonceRequestDto.getUser_id()).get();
        annonce.setUser(userById);
        return annonce;
    }
}

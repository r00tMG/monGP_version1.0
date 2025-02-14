package sn.root.backend_service_mongp.service;

import org.springframework.stereotype.Service;
import sn.root.backend_service_mongp.entities.User;
import sn.root.backend_service_mongp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public final UserRepository userRepository ;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}

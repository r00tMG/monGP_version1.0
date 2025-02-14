package sn.root.backend_service_mongp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.root.backend_service_mongp.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}

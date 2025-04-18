package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface RezervareRepository extends MongoRepository<RezervareEntity, String> {
    List<RezervareEntity> findAllByApartament_IdProprietar(String idProprietar);
    List<RezervareEntity> findAllByUser_Id(String idUser);
}

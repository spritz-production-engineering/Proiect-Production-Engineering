package ro.unibuc.hello.service;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import ro.unibuc.hello.data.ApartamentEntity;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.RezervareEntity;
import ro.unibuc.hello.data.RezervareRepository;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.data.ApartamentRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.RezervareDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RezervareService {

    @Autowired
    private RezervareRepository rezervareRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApartamentRepository apartamentRepository;

    public List<RezervareEntity> getAllReservationsByOwnerId(String id_proprietar) {
        List<RezervareEntity> rezervari = rezervareRepository.findAllByApartament_IdProprietar(id_proprietar);
        
        return rezervari.stream()
                .collect(Collectors.toList());
    }

    public List<RezervareEntity> getAllReservationsByUserId(String id_user) {
        List<RezervareEntity> rezervari = rezervareRepository.findAllByUser_Id(id_user);
        
        return rezervari.stream()
                .collect(Collectors.toList());
    }

    public RezervareEntity getReservationById(String id_rezervare) {
        RezervareEntity rezervare = rezervareRepository.findById(id_rezervare)
                                    .orElseThrow(() -> new EntityNotFoundException(id_rezervare));

        return rezervare; 
    }

    public RezervareEntity saveRezervare(RezervareDto rezervareReq) {
        RezervareEntity rezervare = new RezervareEntity();
        
        if(rezervareReq.getIdApartament() == null ||
            rezervareReq.getIdUser() == null ||
            rezervareReq.getStartDate() == null ||
            rezervareReq.getEndDate() == null
        ) 
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nu sunt destule informații pentru a crea o rezervare");
        }

        rezervare.setId(rezervareReq.getId());

        ApartamentEntity apartament = apartamentRepository.findById(rezervareReq.getIdApartament())
            .orElseThrow(() -> new RuntimeException("Apartament not found"));
        rezervare.setApartament(apartament);

        UserEntity user = userRepository.findById(rezervareReq.getIdUser())
            .orElseThrow(() -> new RuntimeException("User not found"));
        rezervare.setUser(user);

        rezervare.setStartDate(rezervareReq.getStartDate());

        rezervare.setEndDate(rezervareReq.getEndDate());

        rezervare.setActive(false);

        rezervareRepository.save(rezervare);
        
        return rezervare;
    }

    public RezervareEntity updateRezervare(String id_rezervare) {
        RezervareEntity rezervare = rezervareRepository.findById(id_rezervare)
                                    .orElseThrow(() -> new EntityNotFoundException(id_rezervare));

        rezervare.setActive(!rezervare.isActive());

        rezervareRepository.save(rezervare);
        
        return rezervare;
    }

    public void deleteRezervare(String id_rezervare) throws EntityNotFoundException {
        RezervareEntity rezervare = rezervareRepository.findById(id_rezervare)
                                    .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id_rezervare)));
                                    
        rezervareRepository.delete(rezervare);
    }

    public void deleteAllRezervare() {
        rezervareRepository.deleteAll();
    }
}

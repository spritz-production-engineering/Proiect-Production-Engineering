package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public List<RezervareDto> getAllReservationsByOwnerId(String id_proprietar) {
        List<RezervareEntity> rezervari = rezervareRepository.findAllById(List.of(id_proprietar));
        return rezervari.stream()
                .map(rezervare -> new RezervareDto(rezervare.getId()))
                .collect(Collectors.toList());
    }

    public RezervareDto saveRezervare(RezervareDto rezervareReq) {
        RezervareEntity rezervare = new RezervareEntity();

        if (rezervareReq.getIdApartament() != null) {
            ApartamentEntity apartament = apartamentRepository.findById(rezervareReq.getIdApartament())
                .orElseThrow(() -> new RuntimeException("Apartament not found"));
            rezervare.setApartament(apartament);
        }

        if (rezervareReq.getIdUser() != null) {
            UserEntity user = userRepository.findById(rezervareReq.getIdUser())
                .orElseThrow(() -> new RuntimeException("User not found"));
            rezervare.setUser(user);
        }

        if(rezervareReq.getStartDate() != null) {
            rezervare.setStartDate(rezervareReq.getStartDate());
        }

        if(rezervareReq.getEndDate() != null) {
            rezervare.setEndDate(rezervareReq.getEndDate());
        }

        rezervare.setActive(false);

        rezervareRepository.save(rezervare);
        
        return new RezervareDto(rezervare.getId());
    }

    public RezervareDto updateRezervare(String id_proprietar, String id_rezervare) {
        RezervareEntity rezervare = rezervareRepository.findById(id_rezervare)
                                    .orElseThrow(() -> new EntityNotFoundException(id_rezervare));

        rezervare.setActive(true);

        rezervareRepository.save(rezervare);
        
        return new RezervareDto(rezervare.getId());
    }
}

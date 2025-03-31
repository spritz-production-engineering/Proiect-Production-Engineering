package ro.unibuc.hello.service;

import ro.unibuc.hello.data.ApartamentEntity;
import ro.unibuc.hello.data.ApartamentRepository;
import ro.unibuc.hello.dto.Apartament;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApartamentService {

    @Autowired
    private ApartamentRepository apartamentRepository;

    public List<ApartamentEntity> getAllApartamente(String tara, String oras) {
        List<ApartamentEntity> apartamente = apartamentRepository.findAll();
        
        return apartamente.stream()
                .filter(a -> (tara == null || a.getTara().equalsIgnoreCase(tara)))
                .filter(a -> (oras == null || a.getOras().equalsIgnoreCase(oras)))
                .collect(Collectors.toList());
    }
    

    public ApartamentEntity createApartament(Apartament apartament) 
    {
        ApartamentEntity apartamentNou = new ApartamentEntity();

        apartamentNou.setEtaj(apartament.getEtaj());
        apartamentNou.setIdLocatie(apartament.getIdLocatie());
        apartamentNou.setIdProprietar(apartament.getIdProprietar());
        apartamentNou.setNumarApartament(apartament.getNumarApartament());
        apartamentNou.setNumarBai(apartament.getNumarBai());
        apartamentNou.setNumarCamere(apartament.getNumarCamere());
        apartamentNou.setNumarPaturi(apartament.getNumarPaturi());
        apartamentNou.setOras(apartament.getOras());
        apartamentNou.setTara(apartament.getTara());

        apartamentRepository.save(apartamentNou);

        return apartamentNou;
    }
}

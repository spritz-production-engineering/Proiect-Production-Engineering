package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.dto.Proprietar;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.InvalidCNPException;
import ro.unibuc.hello.data.ProprietarEntity;
import ro.unibuc.hello.data.ProprietarRepository;
import ro.unibuc.hello.util.CNPValidator;

import java.util.List;
import java.util.Optional;

@Component
public class ProprietarService {
    @Autowired
    private ProprietarRepository proprietarRepository;

    public ProprietarService(ProprietarRepository proprietarRepository){
        this.proprietarRepository = proprietarRepository;
    }

    public List<ProprietarEntity> getAllProprietari(){
        return proprietarRepository.findAll();
    }

    public Optional<ProprietarEntity> getProprietarById(String id){
        return proprietarRepository.findById(id);
    }

    public Proprietar createProprietar(Proprietar proprietarDTO) {

        if (!CNPValidator.isValidCNP(proprietarDTO.getCnp())) {
            throw new InvalidCNPException("CNP-ul introdus nu este valid!");
        }

        ProprietarEntity proprietarEntity = new ProprietarEntity(
            proprietarDTO.getId(), 
            proprietarDTO.getNume(), 
            proprietarDTO.getPrenume(), 
            proprietarDTO.getEmail(),
            proprietarDTO.getCnp()
        );
        proprietarEntity = proprietarRepository.save(proprietarEntity);
        return new Proprietar(
            proprietarEntity.getId(),
            proprietarEntity.getNume(),
            proprietarEntity.getPrenume(),
            proprietarEntity.getEmail(),
            proprietarEntity.getCnp()
        );
    }

    public Optional<ProprietarEntity> updateProprietar(String id, ProprietarEntity proprietarDetails){
        return proprietarRepository.findById(id).map(existingProprietar -> {
        
        if (!CNPValidator.isValidCNP(proprietarDetails.getCnp())) {
            throw new InvalidCNPException("CNP-ul introdus nu este valid!");
        }

        existingProprietar.setNume(proprietarDetails.getNume());
        existingProprietar.setPrenume(proprietarDetails.getPrenume());
        existingProprietar.setEmail(proprietarDetails.getEmail());
        existingProprietar.setCnp(proprietarDetails.getCnp());

        return proprietarRepository.save(existingProprietar);
    });
    }

     public void deleteProprietar(String id) throws EntityNotFoundException {
        ProprietarEntity entity = proprietarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        proprietarRepository.delete(entity);
    }

    public void deleteAllProprietari() {
        proprietarRepository.deleteAll();
    }
}
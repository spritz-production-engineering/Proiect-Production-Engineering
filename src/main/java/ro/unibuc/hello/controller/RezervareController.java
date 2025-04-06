package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.data.RezervareEntity;
import ro.unibuc.hello.dto.RezervareDto;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.RezervareService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class RezervareController {

    @Autowired
    private RezervareService rezervareService;

    @GetMapping("/api/rezervare/proprietar/{id_proprietar}")
    @ResponseBody
    public List<RezervareEntity> getReservationsByOwnerId(@PathVariable String id_proprietar) {
        return rezervareService.getAllReservationsByOwnerId(id_proprietar);
    }

    @GetMapping("/api/rezervare/user/{id_user}")
    @ResponseBody
    public List<RezervareEntity> getReservationsByUserId(@PathVariable String id_user) {
        return rezervareService.getAllReservationsByUserId(id_user); 
    }

    @GetMapping("/api/rezervare/{id_rezervare}")
    @ResponseBody
    public RezervareEntity getReservationById(@PathVariable String id_rezervare) {
        return rezervareService.getReservationById(id_rezervare);
    }

    @PostMapping("/api/rezervare")
    @ResponseBody
    public RezervareEntity createRezervare(@RequestBody RezervareDto rezervare) {
        return rezervareService.saveRezervare(rezervare);
    }

    @PutMapping("/api/rezervare/{id_rezervare}")
    @ResponseBody
    public RezervareEntity updateRezervare(@PathVariable String id_rezervare) {
        return rezervareService.updateRezervare(id_rezervare);
    }

    @DeleteMapping("/api/rezervare/{id_rezervare}")
    @ResponseBody
    public String deleteRezervare(@PathVariable String id_rezervare) throws EntityNotFoundException {
        rezervareService.deleteRezervare(id_rezervare);

        return new String("Stergerea a fost facuta cu succes!");
    }
}  


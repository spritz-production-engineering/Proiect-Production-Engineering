package ro.unibuc.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import ro.unibuc.hello.data.ApartamentEntity;
import ro.unibuc.hello.dto.Apartament;
import ro.unibuc.hello.service.ApartamentService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/apartamente")
public class ApartamentController {

    @Autowired
    private ApartamentService apartamentService;

    public ApartamentController(ApartamentService apartamentService) {
        this.apartamentService = apartamentService;
    }

    @GetMapping
    public List<ApartamentEntity> getApartamente(@RequestParam(required = false) String tara,
                                           @RequestParam(required = false) String oras) {
        return apartamentService.getAllApartamente(tara, oras);
    }

    @PostMapping
    public ApartamentEntity create(@RequestBody Apartament apartament)
    {
        return apartamentService.createApartament(apartament);
    }
}
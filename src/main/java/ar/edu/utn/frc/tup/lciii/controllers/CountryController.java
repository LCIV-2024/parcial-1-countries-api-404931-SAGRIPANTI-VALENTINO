package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.model.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        return ResponseEntity.ok(countryService.mapToDTOList());
    }
    @GetMapping("/countries?name= {country}")
    public ResponseEntity<CountryDTO> getCountryByName(@PathVariable(name = "country") String country) {
        return ResponseEntity.ok(countryService.getCountryByName(country));
    }

}
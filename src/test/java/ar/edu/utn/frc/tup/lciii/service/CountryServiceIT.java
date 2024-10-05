package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.model.CountryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CountryServiceIT {
    @Autowired
    private CountryService countryService;
    @Test
    void getAllCountries() {
        List<Country> response = countryService.getAllCountries();
        assertNotNull(response);
        assertEquals(250, response.size());
    }
    @Test
    void borders() {
        CountryDTO test = new CountryDTO("CHN", "China");
        CountryDTO response = countryService.getCountryMostBorders();

        assertEquals(test, response);
    }
    @Test
    void byCode() {
        CountryDTO test = new CountryDTO("CHN", "China");
        CountryDTO response = countryService.getCountryByCode("CHN");
        assertEquals(test, response);
    }
}
package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.model.CountryDTO;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class CountryServiceTest {
    @SpyBean
    private CountryService countryService;
    @MockBean
    private RestTemplate restTemplate;
//    @MockBean
//    private ModelMapper modelMapper;
    @MockBean
    private CountryRepository countryRepository;
    @Test
    void getAllCountries() {
        //GIVEN
        Map<String, Object> mapita = new HashMap<>();
        List<Country> responseApiMock = List.of(
                new Country("nombre", 1, 12, "cod", "region", List.of("string"), Map.of("hol", "hola")),
                new Country("nombre", 1, 12, "cod", "region", List.of("string"), Map.of("hol", "hola")),
                new Country("nombre", 1, 12, "cod", "region", List.of("string"), Map.of("hol", "hola"))
        );
//        List<Map<String, Object>> mapa = List.of(
//                Map.of("string", responseApiMock)
//        );
        mapita.put("key", responseApiMock);
        List<Map<String, Object>> mapa = List.of(
                mapita
        );
        //WHEN
        when(restTemplate.getForObject("hola", List.class)).thenReturn(mapa);
        //THEN
        List<Country> response = countryService.getAllCountries();

        assertNotNull(response);
        assertEquals(3, response.size());
    }

    @Test
    void mapToDTOList() {
        //WHEN

    }

    @Test
    void getCountryByName() {
    }

    @Test
    void getCountryByCode() {
    }

    @Test
    void getContriesByContinent() {
    }

    @Test
    void getCountriesByLanguage() {
    }

    @Test
    void getCountryMostBorders() {
        //GIVEN
        List<Country> responseApiMock = List.of(
                new Country("nombre", 1, 12, "codWin", "region", List.of("string", "string"), Map.of("hol", "hola")),
                new Country("nombre", 1, 12, "cod", "region", List.of("string"), Map.of("hol", "hola")),
                new Country("nombre", 1, 12, "cod", "region", List.of("string"), Map.of("hol", "hola"))
        );
        //WHEN
        when(countryService.getAllCountries()).thenReturn(responseApiMock);
        //THEN
        CountryDTO response = countryService.getCountryMostBorders();

        assertNotNull(response);
        assertEquals("codWin", response.getCode());
    }

    @Test
    void saveCountries() {
    }

    @Test
    void mapToCountry() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> auxMap1 = Map.of("commom", "nombre");
        //map.put("commom", "nombre");
        Country aux = new Country("nombre", 1, 12, "codWin", "region", List.of("string", "string"), Map.of("hol", "hola"));
        map.put("name", Map.of("commom",auxMap1));
        Country test = countryService.mapToCountry(map);
        System.out.println(test);
    }

    @Test
    void mapToDTO() {
        Country aux = new Country("nombre", 1, 12, "codWin", "region", List.of("string", "string"), Map.of("hol", "hola"));
        CountryDTO test = new CountryDTO("codWin", "nombre");
        CountryDTO response = countryService.mapToDTO(aux);

        assertEquals(test.getCode(), response.getCode());
    }
}
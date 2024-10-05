package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.model.CountryDTO;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class CountryService {
        @Autowired
        private  CountryRepository countryRepository;
        @Autowired
        private RestTemplate restTemplate;
        @Autowired
        private ModelMapper modelMapper;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        public Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .code((String) countryData.get("cca3"))
                        .borders((List<String>) countryData.get("borders"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .build();
        }

        public CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }

        public List<CountryDTO> mapToDTOList() {
                List<Country> countries = getAllCountries();
                List<CountryDTO> response = new ArrayList<>();
                countries.forEach(c -> {
                        response.add(mapToDTO(c));
                });
                return response;
        }
        public CountryDTO getCountryByName(String country) {
                List<CountryDTO> allCountries = mapToDTOList();
                for (CountryDTO c : allCountries) {
                        if (c.getName().equals(country)) {
                                return c;
                        }
                }
                return null;
        }
        public CountryDTO getCountryByCode(String code) {
                List<CountryDTO> allCountries = mapToDTOList();
                for (CountryDTO c : allCountries) {
                        if (c.getCode().equals(code)) {
                                return c;
                        }
                }
                return null;
        }
        public List<CountryDTO> getContriesByContinent(String continent) {
                List<CountryDTO> response = new ArrayList<>();
                List<Country> allCountries = getAllCountries();

                for (Country c : allCountries) {
                        //System.out.println(c.getRegion());
                        if (c.getRegion().equals(continent)) {
                                response.add(mapToDTO(c));
                        }
                }

                return response;
        }
        public List<CountryDTO> getCountriesByLanguage(String language) {
                List<CountryDTO> response = new ArrayList<>();
                List<Country> allCountries = getAllCountries();

                for (Country c : allCountries) {
                        //System.out.println(c.getLanguages().toString());
                        if (c.getLanguages() != null) {
                                if (c.getLanguages().containsValue(language)) {
                                        response.add(mapToDTO(c));
                                }
                        }
                }
                return response;
        }
        public CountryDTO getCountryMostBorders() {
                Integer aux = 0;
                List<Country> allCountries = getAllCountries();

                CountryDTO response = new CountryDTO();
                for (Country c : allCountries) {
                        System.out.println(c.toString());
                        if (c.getBorders() != null) {
                                if (c.getBorders().size() > aux) {
                                        aux = c.getBorders().size();
                                        response = mapToDTO(c);
                                }
                        }

                }
                return response;
        }
        public List<CountryDTO> saveCountries(Integer amount) {
                if (amount > 10) {
                        throw new RuntimeException("La cantidad no debe superar los 10 paises");
                }
                List<Country> allCountries = getAllCountries();
                List<CountryEntity> entities = new ArrayList<>();
                List<CountryDTO> response = new ArrayList<>();
                List<Country> subList = allCountries.subList(0, amount);

                for (Country c : subList) {
                        entities.add(countryRepository.save(modelMapper.map(c, CountryEntity.class)));
                }
                for (CountryEntity ce : entities) {
                        response.add(mapToDTO(modelMapper.map(ce, Country.class)));
                }
                return response;
        }
}
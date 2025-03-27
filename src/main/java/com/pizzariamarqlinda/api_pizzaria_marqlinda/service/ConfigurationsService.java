package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Configuration;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.ConfigurationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfigurationsService {

    private final ConfigurationsRepository repository;

    public Map<String, List<String>> loadConfigurations(){
        return repository.findAll()
                .stream()
                .collect(Collectors.toMap(Configuration::getKey, Configuration::getValues));
    }
    
    public Integer getOpeningHour(){
        return Integer.parseInt(loadConfigurations().get("OPENING_HOURS").getFirst());
    }

    public Integer getOpeningMinute(){
        return Integer.parseInt(loadConfigurations().get("OPENING_HOURS").get(1));
    }

    public Integer getClosingHour(){
        return Integer.parseInt(loadConfigurations().get("CLOSING_TIME").getFirst());
    }

    public Integer getClosingMinute(){
        return Integer.parseInt(loadConfigurations().get("CLOSING_TIME").get(1));
    }


}

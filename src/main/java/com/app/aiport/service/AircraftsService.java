package com.app.aiport.service;

import javax.transaction.Transactional;
import java.util.List;

import com.app.aiport.entity.Aircraft;
import com.app.aiport.repository.AircraftsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class AircraftsService {

    private final AircraftsRepository aircraftsRepository;

    private final String DELETED = "Aircraft deleted, with code: ";

    public List<Aircraft> getAircrafts(String model) {
        return isNull(model) ? aircraftsRepository.findAll() :
                aircraftsRepository.findAircraftByModelContaining(model);
    }

    public Aircraft getAircraftById(String id) {
        return aircraftsRepository.getById(id);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public Aircraft saveNewAircraft(Aircraft aircraft) {
        log.debug("Saving new aircraft with code: " + aircraft.getCode() + ", and model: " + aircraft.getModel());
        return aircraftsRepository.save(aircraft);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public String deleteAircraftByCode(String code) {
        log.debug("Deleting aircraft with id: " + code);
        aircraftsRepository.deleteById(code);
        return DELETED;
    }
}

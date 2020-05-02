package softuni.residentevil.service;

import softuni.residentevil.domain.models.service.CapitalServiceModel;

import java.util.List;

public interface CapitalService {
    List<CapitalServiceModel> findAllCapitals();
    CapitalServiceModel findCapitalById(String id);
}

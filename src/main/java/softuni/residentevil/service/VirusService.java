package softuni.residentevil.service;

import softuni.residentevil.domain.models.binding.VirusAddBindingModel;
import softuni.residentevil.domain.models.service.VirusServiceModel;

import java.util.ArrayList;
import java.util.List;

public interface VirusService {
    void spreadVirus(VirusServiceModel virusServiceModel);
    List<VirusServiceModel> findAllViruses();
    VirusServiceModel findVirusById(String id);
    void editVirus(VirusAddBindingModel virusAddBindingModel);
    void deleteVirus(VirusServiceModel virusServiceModel);
    ArrayList<Double> extractVirusesAsList();
}

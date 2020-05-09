package softuni.residentevil.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.residentevil.domain.entities.Capital;
import softuni.residentevil.domain.entities.Virus;
import softuni.residentevil.domain.models.binding.VirusAddBindingModel;
import softuni.residentevil.domain.models.service.CapitalServiceModel;
import softuni.residentevil.domain.models.service.VirusServiceModel;
import softuni.residentevil.repository.CapitalRepository;
import softuni.residentevil.repository.VirusRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirusServiceImpl implements VirusService{
    private final ModelMapper modelMapper;
    private final VirusRepository virusRepository;
    private final CapitalRepository capitalRepository;
    private final CapitalService capitalService;
    private final Gson gson;
    @Autowired
    public VirusServiceImpl(ModelMapper modelMapper, VirusRepository virusRepository, CapitalRepository capitalRepository, CapitalService capitalService, Gson gson) {
        this.modelMapper = modelMapper;
        this.virusRepository = virusRepository;
        this.capitalRepository = capitalRepository;
        this.capitalService = capitalService;
        this.gson = gson;
    }
    @Transactional
    @Override
    public void spreadVirus(VirusServiceModel virusServiceModel) {
        Virus virus = this.modelMapper.map(virusServiceModel,Virus.class);
        List<Capital> capitalsList = virusServiceModel.getCapitals().stream().map(c->this.modelMapper.map(c,Capital.class))
                .collect(Collectors.toList());

        virus.setCapitals(capitalsList);

        this.virusRepository.saveAndFlush(virus);
    }

    @Override
    public List<VirusServiceModel> findAllViruses() {
        return this.virusRepository.findAll().stream()
                .map(v->this.modelMapper.map(v,VirusServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public VirusServiceModel findVirusById(String id) {
        Virus virus = this.virusRepository.findById(id).orElse(null);
        if(virus!=null){
         return this.modelMapper.map(virus, VirusServiceModel.class);
        }
        return null;
    }

    @Override
    public void editVirus(VirusAddBindingModel virusAddBindingModel) {
        List<CapitalServiceModel> capitals = Arrays.stream(virusAddBindingModel.getCapitalsIds())
                .map(this.capitalService::findCapitalById).collect(Collectors.toList());
        List<Capital> capitalList= capitals.stream().map(c->this.modelMapper.map(c, Capital.class)).collect(Collectors.toList());

        Virus virus = this.modelMapper.map(virusAddBindingModel,Virus.class);
        virus.setCapitals(capitalList);
        this.virusRepository.save(virus);
    }

    @Override
    public void deleteVirus(VirusServiceModel virusServiceModel) {
        this.virusRepository.delete(this.modelMapper.map(virusServiceModel,Virus.class));
    }

    @Override
    public ArrayList<Double> extractVirusesAsList() {
        List<Virus> virusList = this.virusRepository.findAll();
        ArrayList<Double>result = new ArrayList<>();
        List<Capital>capitals = new ArrayList<>();
        virusList.forEach(virus -> {
            capitals.addAll(virus.getCapitals());
        });
        capitals.forEach(c->{
            result.add(c.getLatitude());
            result.add(c.getLongitude());
        });
        return result;
    }
}

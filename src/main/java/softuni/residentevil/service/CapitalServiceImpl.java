package softuni.residentevil.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.residentevil.domain.entities.Capital;
import softuni.residentevil.domain.models.service.CapitalServiceModel;
import softuni.residentevil.domain.models.view.CapitalViewModel;
import softuni.residentevil.repository.CapitalRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CapitalServiceImpl implements CapitalService {
    private final CapitalRepository capitalRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
@Autowired
    public CapitalServiceImpl(CapitalRepository capitalRepository, ModelMapper modelMapper, Gson gson) {
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    this.gson = gson;
}

    @Override
    public List<CapitalServiceModel> findAllCapitals() {
    return this.capitalRepository.findAll().stream()
            .map(c->this.modelMapper.map(c, CapitalServiceModel.class)).collect(Collectors.toList());

    }

    @Override
    public CapitalServiceModel findCapitalById(String id) {
        Capital capital= this.capitalRepository.findById(id).orElse(null);
        if(capital!=null){
            System.out.println();
            return this.modelMapper.map(capital, CapitalServiceModel.class);
        }else {
            return null;
        }
    }

    @Override
    public String extractCapitalsAsJson() {
    List<CapitalViewModel> capitals = this.capitalRepository.findAll().
            stream().
            map(c->this.modelMapper.map(c, CapitalViewModel.class)).collect(Collectors.toList());
        return gson.toJson(capitals);
    }
}

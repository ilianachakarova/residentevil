package softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.domain.models.binding.VirusAddBindingModel;
import softuni.residentevil.domain.models.service.CapitalServiceModel;
import softuni.residentevil.domain.models.service.VirusServiceModel;
import softuni.residentevil.domain.models.view.CapitalListViewModel;
import softuni.residentevil.service.CapitalService;
import softuni.residentevil.service.VirusService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {
    private final CapitalService capitalService;
    private final ModelMapper modelMapper;
    private final VirusService virusService;
    @Autowired
    public VirusController(CapitalService capitalService, ModelMapper modelMapper, VirusService virusService) {
        this.capitalService = capitalService;
        this.modelMapper = modelMapper;
        this.virusService = virusService;
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") VirusAddBindingModel virusAddBindingModel){
        modelAndView.addObject("bindingModel", virusAddBindingModel);
        modelAndView.addObject("capitals",this.capitalService.findAllCapitals().
                stream().
                map(c->this.modelMapper.map(c, CapitalListViewModel.class)).collect(Collectors.toList()));
        return super.view("add-virus", modelAndView);
    }

    @PostMapping("/add")
   public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel")
                                              VirusAddBindingModel virusAddBindingModel, BindingResult bindingResult, ModelAndView modelAndView){
        if(bindingResult.hasErrors()){
            modelAndView.addObject("bindingModel",virusAddBindingModel);
            return super.view("add-virus", modelAndView);
        }

        VirusServiceModel virusServiceModel = this.modelMapper.map(virusAddBindingModel, VirusServiceModel.class);
        this.turnCapitalModels(virusServiceModel, virusAddBindingModel);
        this.virusService.spreadVirus(virusServiceModel);

        return super.redirect("/");
   }

    private void turnCapitalModels(VirusServiceModel virusServiceModel, VirusAddBindingModel virusAddBindingModel) {
        List<CapitalServiceModel>capitalServiceModels =
                Arrays.stream(virusAddBindingModel.getCapitalsIds())
                        .map(this.capitalService::findCapitalById)
                .collect(Collectors.toList());

        virusServiceModel.setCapitals(capitalServiceModels);
    }
}

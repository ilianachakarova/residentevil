package softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.domain.models.view.CapitalViewModel;
import softuni.residentevil.domain.models.view.VirusViewModel;
import softuni.residentevil.service.CapitalService;
import softuni.residentevil.service.VirusService;

import java.util.stream.Collectors;

@Controller
public class ShowVirusesController extends BaseController {
    private final VirusService virusService;
    private final ModelMapper modelMapper;
    private final CapitalService capitalService;
    @Autowired
    public ShowVirusesController(VirusService virusService, ModelMapper modelMapper, CapitalService capitalService) {
        this.virusService = virusService;
        this.modelMapper = modelMapper;
        this.capitalService = capitalService;
    }

    @GetMapping("/viruses/show")
    @PreAuthorize(value = "isAuthenticated()")
    public ModelAndView showViruses(ModelAndView modelAndView, @ModelAttribute(name = "viewModel") VirusViewModel virusViewModel,
                                    @ModelAttribute(name = "capitalsModel") CapitalViewModel capitalViewModel) {
        modelAndView.addObject("viewModel", virusViewModel);
        modelAndView.addObject("viruses",
                this.virusService.findAllViruses().stream().map
                         (v->{
                           VirusViewModel virus=  this.modelMapper.map(v,VirusViewModel.class);
                           virus.setReleasedOn(v.getReleasedOn().toString());
                             return virus;
                         }).collect(Collectors.toList()));
        modelAndView.addObject("capitalsModel",capitalViewModel);
        modelAndView.addObject("capitals",
                this.capitalService.findAllCapitals().stream()
        .map(c->this.modelMapper.map(c,CapitalViewModel.class)).collect(Collectors.toList()));
        return super.view("show-viruses",modelAndView);
    }
}

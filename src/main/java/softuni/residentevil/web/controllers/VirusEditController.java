package softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.domain.models.binding.VirusAddBindingModel;
import softuni.residentevil.domain.models.view.CapitalListViewModel;
import softuni.residentevil.service.CapitalService;
import softuni.residentevil.service.VirusService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class VirusEditController extends BaseController{
    private final ModelMapper modelMapper;
    private final VirusService virusService;
    private final CapitalService capitalService;
    @Autowired
    public VirusEditController(ModelMapper modelMapper, VirusService virusService, CapitalService capitalService) {
        this.modelMapper = modelMapper;
        this.virusService = virusService;
        this.capitalService = capitalService;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editVirus(ModelAndView modelAndView, @PathVariable("id") String id){
        VirusAddBindingModel virusAddBindingModel =
                this.modelMapper.map(this.virusService.findVirusById(id),VirusAddBindingModel.class);
        modelAndView.addObject("bindingModel", virusAddBindingModel);
        modelAndView.addObject("capitals",this.capitalService.findAllCapitals().
                stream().
                map(c->this.modelMapper.map(c, CapitalListViewModel.class)).collect(Collectors.toList()));

        return super.view("edit-virus",modelAndView);
    }
    @PostMapping("/edit/{id}")
    public ModelAndView confirmEdit(@PathVariable("id")String id,@Valid @ModelAttribute(name = "bindingModel") VirusAddBindingModel virusAddBindingModel,
                                    BindingResult bindingResult,
                                    ModelAndView modelAndView){
            if(bindingResult.hasErrors()){
                modelAndView.addObject("capitals",this.capitalService.findAllCapitals().
                        stream().
                        map(c->this.modelMapper.map(c, CapitalListViewModel.class)).collect(Collectors.toList()));

                return super.view("edit-virus",modelAndView);
            }

            this.virusService.editVirus(virusAddBindingModel);
            return super.redirect("/viruses/show");
    }


    @GetMapping("/delete/{id}")
    public ModelAndView deleteVirus(@PathVariable("id") String id, ModelAndView modelAndView){
        VirusAddBindingModel virusAddBindingModel =
                this.modelMapper.map(this.virusService.findVirusById(id),VirusAddBindingModel.class);
        modelAndView.addObject("bindingModel", virusAddBindingModel);
        modelAndView.addObject("capitals",this.capitalService.findAllCapitals().
                stream().
                map(c->this.modelMapper.map(c, CapitalListViewModel.class)).collect(Collectors.toList()));

        return super.view("delete-virus",modelAndView);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView confirmDelete(@PathVariable("id")String id, ModelAndView modelAndView){
        this.virusService.deleteVirus(this.virusService.findVirusById(id));
        return redirect("/viruses/show");
    }
}

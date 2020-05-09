package softuni.residentevil.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.service.CapitalService;
import softuni.residentevil.service.VirusService;

@Controller
public class MapController extends BaseController{
    private final VirusService virusService;
    private final CapitalService capitalService;
    @Autowired
    public MapController(VirusService virusService, CapitalService capitalService) {
        this.virusService = virusService;
        this.capitalService = capitalService;
    }

    @GetMapping("/map")
    public ModelAndView showMap(ModelAndView modelAndView){
        modelAndView.addObject("coordinates", virusService.extractVirusesAsList());
        return super.view("map",modelAndView);
    }
}

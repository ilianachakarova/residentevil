package softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.domain.models.binding.UserRegisterBindingModel;
import softuni.residentevil.domain.models.service.UserServiceModel;
import softuni.residentevil.service.UserService;

@Controller
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(ModelAndView modelAndView){
        return super.view("register",modelAndView);
    }
@PostMapping("/register")
    public ModelAndView registerConfirm(ModelAndView modelAndView, @ModelAttribute(name = "model")UserRegisterBindingModel model){
        if(!model.getConfirmPassword().equals(model.getPassword())){
            return super.view("register", modelAndView);
        }
        this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class));
        return super.redirect("/login");
    }
    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(ModelAndView modelAndView){
        return super.view("login", modelAndView);
    }





}

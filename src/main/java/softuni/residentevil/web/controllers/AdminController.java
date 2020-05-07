package softuni.residentevil.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.domain.models.view.UserViewModel;
import softuni.residentevil.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController extends BaseController {

    private final UserService userService;
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView showUsers(ModelAndView modelAndView, Principal principal){
        List<UserViewModel> users = this.userService.findAllUsers().stream()
                .filter(u->!u.getUsername().equals(principal.getName())).collect(Collectors.toList());
        modelAndView.addObject("users",users);

        return super.view("all-users", modelAndView);
    }
}

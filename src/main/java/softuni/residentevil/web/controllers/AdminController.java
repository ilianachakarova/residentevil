package softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.domain.models.binding.EditUserBindingModel;
import softuni.residentevil.domain.models.view.UserViewModel;
import softuni.residentevil.service.RoleService;
import softuni.residentevil.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    @Autowired
    public AdminController(UserService userService, ModelMapper modelMapper, RoleService roleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    @GetMapping("/admin/users")
    public ModelAndView showUsers(ModelAndView modelAndView, Principal principal){
        List<UserViewModel> users = this.userService.findAllUsers().stream()
                .filter(u->!u.getUsername().equals(principal.getName())).collect(Collectors.toList());
        modelAndView.addObject("users",users);
        modelAndView.addObject("user",principal.getName());
        return super.view("all-users", modelAndView);
    }

    @GetMapping("/users/edit/{id}")
    public ModelAndView editUser(@PathVariable("id")String id,
                                 ModelAndView modelAndView, Principal principal){

        UserViewModel model = this.userService.findUserById(id);
        List<String>roles = this.roleService.findAllRoles();
        modelAndView.addObject("user", model);
        modelAndView.addObject("roles",roles);


        return super.view("edit-user", modelAndView);

    }

    @PostMapping("/users/edit/{id}")
    public ModelAndView editUserConfirm(@PathVariable("id")String id, @ModelAttribute(name = "editModel") EditUserBindingModel editUserBindingModel,
                                        ModelAndView modelAndView){

        this.userService.editUserRole(editUserBindingModel);
        return super.redirect("/admin/users");
    }
}

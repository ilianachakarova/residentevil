package softuni.residentevil.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.residentevil.domain.models.service.UserServiceModel;
import softuni.residentevil.domain.models.view.UserViewModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);
    List<UserViewModel> findAllUsers();

}

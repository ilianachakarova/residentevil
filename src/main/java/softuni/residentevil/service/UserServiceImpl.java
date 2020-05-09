package softuni.residentevil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.residentevil.domain.entities.Role;
import softuni.residentevil.domain.entities.User;
import softuni.residentevil.domain.models.binding.EditUserBindingModel;
import softuni.residentevil.domain.models.service.UserServiceModel;
import softuni.residentevil.domain.models.view.UserViewModel;
import softuni.residentevil.repository.RoleRepository;
import softuni.residentevil.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        this.seedRolesInDb();
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        this.giveRolesToUser(user);
        try {
            this.userRepository.saveAndFlush(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<UserViewModel> findAllUsers() {
        List<User> usersEntities = this.userRepository.findAll();
        List<UserViewModel> userViewModels = new ArrayList<>();
        for (User usersEntity : usersEntities) {
            UserViewModel userViewModel = this.modelMapper.map(usersEntity, UserViewModel.class);
            userViewModel.setRoles(usersEntity.getAuthorities()
                    .stream().map(Role::getAuthority).collect(Collectors.joining(", ")));
            userViewModels.add(userViewModel);
        }
        return userViewModels;
    }

    @Override
    public UserViewModel findUserById(String id) {

        User entity = this.userRepository.findById(id).orElse(null);
        if (entity != null) {

            UserViewModel userViewModel =
                    this.modelMapper.map(entity, UserViewModel.class);
            userViewModel.setRoles(entity.getAuthorities().stream().map(Role::getAuthority).collect(Collectors.joining(", ")));
            return userViewModel;
        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }

    @Override
    public void editUserRole(EditUserBindingModel editUserBindingModel) {
        User entity = this.userRepository.findById(editUserBindingModel.getId()).orElse(null);
        if(entity!=null){
            Role role = this.roleRepository.findByAuthority(editUserBindingModel.getRole());
            entity.getAuthorities().add(role);
            this.userRepository.save(entity);
        }else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    private void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setAuthority("ROLE_ADMIN");

            Role moderator = new Role();
            moderator.setAuthority("ROLE_MODERATOR");

            Role user = new Role();
            user.setAuthority("ROLE_USER");

            this.roleRepository.saveAndFlush(admin);
            this.roleRepository.saveAndFlush(moderator);
            this.roleRepository.saveAndFlush(user);

        }
    }

    private void giveRolesToUser(User user) {
        if (this.userRepository.count() == 0) {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ADMIN"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_MODERATOR"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
        } else {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
        }
    }
}

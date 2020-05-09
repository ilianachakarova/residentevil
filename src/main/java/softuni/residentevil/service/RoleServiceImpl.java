package softuni.residentevil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.residentevil.domain.entities.Role;
import softuni.residentevil.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<String> findAllRoles() {
        return this.roleRepository.findAll().stream().map(Role::getAuthority).collect(Collectors.toList());
    }
}

package com.example.eksamensprojekt_2sem.Service;

import com.example.eksamensprojekt_2sem.Model.Role;
import com.example.eksamensprojekt_2sem.Repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    //Get all roles
    public List<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }

}

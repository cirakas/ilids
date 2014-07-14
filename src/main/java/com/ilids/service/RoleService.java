package com.ilids.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.dao.RoleRepository;
import com.ilids.domain.Role;

@Component
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.getAll();
    }

    public List<Role> getAllRolesExceptRestrictedOnes() {
        return roleRepository.getAllUsersExceptRestrictedOnes();
    }

    public Role findById(Long id) {
        return roleRepository.findById(id);
    }

    public Role findByName(String name) {
        return roleRepository.findByCustomField("name", name);
    }

    public void delete(String name) {
        roleRepository.delete(findByName(name));
    }

    public void createNewRole(String name) {
        Role role = new Role();
        role.setName(name);
        roleRepository.persist(role);
    }

}

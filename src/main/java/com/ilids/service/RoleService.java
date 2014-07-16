package com.ilids.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.dao.RoleRepository;
import com.ilids.domain.Menu;
import com.ilids.domain.Role;

@Component
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    
     @Autowired
    private MenuService menuService;

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
    public Role remove(Long id) {
        Role role = roleRepository.findById(id);
        if (role == null) {
            throw new IllegalArgumentException();
        }
        //  device.getUser().getDevices().remove(device); //pre remove
        roleRepository.delete(role);
        return role;
    }
    public void delete(String name) {
        roleRepository.delete(findByName(name));
    }

    public void createNewRole(String name) {
        Role role = new Role();
        role.setName(name);
        roleRepository.persist(role);
    }

    public Role saveNewRole(Role role){
        roleRepository.persist(role);
        return role;
    }
    
    public void saveMenuItems(Role role){
        String insertQuery="INSERT INTO role_menu(role_id, menu_id) VALUES ";
        String appendQuery="";
        String subQuery="";
        for(String menuId:role.getMenuvalues()){
            if(!"".equals(appendQuery)){
            appendQuery=appendQuery+",";
            }
            subQuery="("+role.getId()+","+Long.valueOf(menuId)+")";
            appendQuery=appendQuery+subQuery;
        }
        insertQuery=insertQuery+appendQuery;
        roleRepository.executeNativeQuery(insertQuery);
    }
    
    
public List<Menu>getAllMenu(){
    return menuService.getAllMenu();
}
}

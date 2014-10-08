package com.ilids.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.dao.RoleRepository;
import com.ilids.domain.Menu;
import com.ilids.domain.Role;
import java.text.ParseException;

@Component
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MenuService menuService;
    
    @Autowired
    private UserService userService;

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

    public boolean remove(Long id) throws Exception{
        boolean result=false;
	boolean countResult=userService.checkRoleUsedOrNot(id);
        if(!countResult){
	deleteRoleFromRoleMenu(id);
	Role role = roleRepository.findById(id);
	//  device.getUser().getDevices().remove(device); //pre remove
	roleRepository.delete(role);
	result=true;
	}
	return result;
    }

    public void delete(String name) {
	roleRepository.delete(findByName(name));
    }

    public void createNewRole(String name) {
	Role role = new Role();
	role.setName(name);
	roleRepository.persist(role);
    }

    public Role saveNewRole(Role role)throws Exception {
	roleRepository.persist(role);
	return role;
    }

    public Role updateRole(Role role)throws Exception {
	roleRepository.merge(role);
	return role;
    }

    public void saveMenuItems(Role role) throws Exception{

	String deleteQuery = "delete from role_menu where role_id=" + role.getId();
	roleRepository.executeNativeQuery(deleteQuery);
	String insertQuery = "INSERT INTO role_menu(role_id, menu_id) VALUES ";
	String appendQuery = "";
	String subQuery = "";
	for (String menuId : role.getMenuvalues()) {
	    if (!"".equals(appendQuery)) {
		appendQuery = appendQuery + ",";
	    }
	    subQuery = "(" + role.getId() + "," + Long.valueOf(menuId) + ")";
	    appendQuery = appendQuery + subQuery;
	}
	insertQuery = insertQuery + appendQuery;
	roleRepository.executeNativeQuery(insertQuery);
    }

    public Role editRole(Long roleId) throws Exception{
	Role role = roleRepository.findById(roleId);
	List<Object> menuObjectList = roleRepository.selectedRoleMenu(roleId);
	role.setMenuObjectList(menuObjectList);
	return role;

    }

    public void deleteRoleFromRoleMenu(Long roleId) throws Exception{
	String deleteQuery = "delete from role_menu where role_id=" + roleId;
	roleRepository.executeNativeQuery(deleteQuery);
    }

    public List<Menu> getAllMenu() {
	return menuService.getAllMenu();
    }
    
    public List<Object> getAllMenuIds(String userName){
	 return roleRepository.getAllMenuIds(userName);
    }
    
    public boolean getAllRoleNameData(String roleName) throws ParseException {
       List<Object[]> roleNameData=roleRepository.getAllRoleNameData(roleName); 
       boolean result=false;
       int roleSize=roleNameData.size();
        if(!roleNameData.isEmpty()){
            result=true;
        }
        return result;
    }  
    
    
}

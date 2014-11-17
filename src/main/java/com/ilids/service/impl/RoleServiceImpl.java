package com.ilids.service.impl;

import com.ilids.IRepository.RoleRepository;
import com.ilids.IService.MenuService;
import com.ilids.IService.RoleService;
import com.ilids.IService.UserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.domain.Menu;
import com.ilids.domain.Role;
import java.text.ParseException;

/**
 *
 * @author cirakas
 */
@Component
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MenuService menuService;
    
    @Autowired
    private UserService userService;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.getAll();
    }

    @Override
    public List<Role> getAllRolesExceptRestrictedOnes() {
	return roleRepository.getAllUsersExceptRestrictedOnes();
    }

    @Override
    public Role findById(Long id) {
	return roleRepository.findById(id);
    }

    @Override
    public Role findByName(String name) {
	return roleRepository.findByCustomField("name", name);
    }

    @Override
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

    @Override
    public void delete(String name) {
	roleRepository.delete(findByName(name));
    }

    @Override
    public void createNewRole(String name) {
	Role role = new Role();
	role.setName(name);
	roleRepository.persist(role);
    }

    @Override
    public Role saveNewRole(Role role)throws Exception {
	roleRepository.persist(role);
	return role;
    }

    @Override
    public Role updateRole(Role role)throws Exception {
	roleRepository.merge(role);
	return role;
    }

    @Override
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

    @Override
    public Role editRole(Long roleId) throws Exception{
	Role role = roleRepository.findById(roleId);
	List<Object> menuObjectList = roleRepository.selectedRoleMenu(roleId);
	role.setMenuObjectList(menuObjectList);
	return role;

    }

    @Override
    public void deleteRoleFromRoleMenu(Long roleId) throws Exception{
	String deleteQuery = "delete from role_menu where role_id=" + roleId;
	roleRepository.executeNativeQuery(deleteQuery);
    }

    @Override
    public List<Menu> getAllMenu() {
	return menuService.getAllMenu();
    }
    
    @Override
    public List<Object> getAllMenuIds(String userName){
	 return roleRepository.getAllMenuIds(userName);
    }
    
    @Override
    public boolean getAllRoleNameData(String roleName , Long id) throws ParseException {
       List<Object[]> roleNameData=roleRepository.getAllRoleNameData(roleName , id); 
       boolean result=true;
       int roleSize=roleNameData.size();
        if(!roleNameData.isEmpty()){
            result=false;
        }
        return result;
    }  
    
    
}

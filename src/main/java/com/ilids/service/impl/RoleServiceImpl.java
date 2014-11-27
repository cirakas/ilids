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
        List<Role> allRoles = roleRepository.getAll();
        roleRepository.close();
        return allRoles;
    }

    @Override
    public List<Role> getAllRolesExceptRestrictedOnes() {
        List<Role> roleExceptRestricted = roleRepository.getAllUsersExceptRestrictedOnes();
        roleRepository.close();
	return roleExceptRestricted;
    }

    @Override
    public Role findById(Long id) {
        Role roleId = roleRepository.findById(id);
        roleRepository.close();
	return roleId;
    }

    @Override
    public Role findByName(String name) {
        Role roleName = roleRepository.findByCustomField("name", name);
        roleRepository.close();
	return roleName;
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
        roleRepository.close();
	return result;
    }

    @Override
    public void delete(String name) {
	roleRepository.delete(findByName(name));
        roleRepository.close();
    }

    @Override
    public void createNewRole(String name) {
	Role role = new Role();
	role.setName(name);
	roleRepository.persist(role);
        roleRepository.close();
    }

    @Override
    public Role saveNewRole(Role role)throws Exception {
	roleRepository.persist(role);
        roleRepository.close();
	return role;
    }

    @Override
    public Role updateRole(Role role)throws Exception {
	roleRepository.merge(role);
        roleRepository.close();
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
        roleRepository.close(); 
    }

    @Override
    public Role editRole(Long roleId) throws Exception{
	Role role = roleRepository.findById(roleId);
	List<Object> menuObjectList = roleRepository.selectedRoleMenu(roleId);
	role.setMenuObjectList(menuObjectList);
        roleRepository.close(); 
	return role;

    }

    @Override
    public void deleteRoleFromRoleMenu(Long roleId) throws Exception{
	String deleteQuery = "delete from role_menu where role_id=" + roleId;
	roleRepository.executeNativeQuery(deleteQuery);
        roleRepository.close(); 
    }

    @Override
    public List<Menu> getAllMenu() {
        List<Menu> allMenu = menuService.getAllMenu();
        roleRepository.close();
	return allMenu;
    }
    
    @Override
    public List<Object> getAllMenuIds(String userName){
         List<Object> allMenuId = roleRepository.getAllMenuIds(userName);
         roleRepository.close();
	 return allMenuId;
    }
    
    @Override
    public boolean getAllRoleNameData(String roleName , Long id) throws ParseException {
       List<Object[]> roleNameData=roleRepository.getAllRoleNameData(roleName , id); 
       boolean result=true;
       int roleSize=roleNameData.size();
        if(!roleNameData.isEmpty()){
            result=false;
        }
        roleRepository.close();
        return result;
    }  
    
    
}

package com.ilids.repository.impl;

import com.ilids.IRepository.RoleRepository;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ilids.domain.Role;

/**
 *
 * @author cirakas
 */
@Component
public class RoleRepositoryImpl extends GenericRepositoryImpl<Role> implements RoleRepository{

    /**
     *
     */
    public RoleRepositoryImpl() {
        super(Role.class);
    }
    
    public List<Role> getAllUsersExceptRestrictedOnes() {
        return super.runCustomQuery(entityManager.createQuery("SELECT c FROM Role c WHERE c.name != 'ROLE_ADMIN' AND c.name != 'ROLE_USER'", Role.class));
    }
    
    public List<Object> selectedRoleMenu(Long roleId)throws Exception{
	List<Object> menuSelectedList=entityManager.createNativeQuery("select menu_id from role_menu where role_id="+roleId).getResultList();
	return menuSelectedList;
    }
    
    public List<Object> getAllMenuIds(String userName){
	List<Object> menuSelectedList=entityManager.createNativeQuery("select rm.menu_id from role_menu rm inner join user u on rm.role_id=u.role where u.username='"+userName+"'").getResultList();
	return menuSelectedList;
    }
    
    public List<Object[]> getAllRoleNameData(String roleName) {
       // Object[] getMailAdd = null;
        //try{
        String selectRoleQuery = "select * from role where name= '"+roleName+"'";
        return (List<Object[]>) entityManager.createNativeQuery(selectRoleQuery).getResultList();
    }
    
}

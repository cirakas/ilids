/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.IRepository;

import com.ilids.domain.Role;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface RoleRepository extends GenericRepository<Role>{

    /**
     *
     * @return
     */
    public List<Role> getAllUsersExceptRestrictedOnes();

    /**
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    public List<Object> selectedRoleMenu(Long roleId) throws Exception;

    /**
     *
     * @param userName
     * @return
     */
    public List<Object> getAllMenuIds(String userName);

    /**
     *
     * @param roleName
     * @return
     */
    public List<Object[]> getAllRoleNameData(String roleName);

}

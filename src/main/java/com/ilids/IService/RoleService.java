/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.IService;

import com.ilids.domain.Menu;
import com.ilids.domain.Role;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface RoleService {

    /**
     *
     * @return
     */
    public List<Role> getAllRoles();

    /**
     *
     * @return
     */
    public List<Role> getAllRolesExceptRestrictedOnes();

    /**
     *
     * @param id
     * @return
     */
    public Role findById(Long id);

    /**
     *
     * @param name
     * @return
     */
    public Role findByName(String name);

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public boolean remove(Long id) throws Exception;

    /**
     *
     * @param name
     */
    public void delete(String name);

    /**
     *
     * @param name
     */
    public void createNewRole(String name);

    /**
     *
     * @param role
     * @return
     * @throws Exception
     */
    public Role saveNewRole(Role role) throws Exception;

    /**
     *
     * @param role
     * @return
     * @throws Exception
     */
    public Role updateRole(Role role) throws Exception;

    /**
     *
     * @param role
     * @throws Exception
     */
    public void saveMenuItems(Role role) throws Exception;

    /**
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    public Role editRole(Long roleId) throws Exception;

    /**
     *
     * @param roleId
     * @throws Exception
     */
    public void deleteRoleFromRoleMenu(Long roleId) throws Exception;

    /**
     *
     * @return
     */
    public List<Menu> getAllMenu();

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
     * @throws ParseException
     */
    public boolean getAllRoleNameData(String roleName) throws ParseException;

}

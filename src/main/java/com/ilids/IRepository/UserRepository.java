/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.IRepository;

import com.ilids.domain.User;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface UserRepository extends GenericRepository<User>{

    /**
     *
     * @return
     */
    public List<User> getAllUsersExceptAdmin();

    /**
     *
     * @param mailAd
     * @return
     */
    public List<Object[]> getAllUserMailData(String mailAd);

    /**
     *
     * @param roleId
     * @return
     */
    public boolean checkRoleUsed(Long roleId);

    /**
     *
     * @param userName
     * @return
     */
    public User getUserByUserName(String userName);

    /**
     *
     * @param id
     * @return
     */
    public User findById(Long id);

}

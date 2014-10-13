/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.IService;

import com.ilids.domain.User;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface UserService {

    /**
     *
     * @return
     */
    public List<User> getAllUsersExceptAdmin();

    /**
     *
     * @param roleId
     * @param userId
     * @return
     */
    public boolean addRoleToUser(Long roleId, Long userId);

    /**
     *
     * @param roleId
     * @param userId
     * @return
     */
    public boolean takeRoleFromUser(Long roleId, Long userId);

    /**
     *
     * @param user
     * @return
     * @throws Exception
     */
    public boolean addNewUserToDatabase(User user) throws Exception;

    /**
     *
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateNewUserToDatabase(User user) throws Exception;

    /**
     *
     * @param user
     */
    public void encryptPassword(User user);

    /**
     *
     * @param userId
     * @throws Exception
     */
    public void removeUserFromDatabase(Long userId) throws Exception;

    /**
     *
     * @param user
     */
    public void persist(User user);

    /**
     *
     * @param user
     */
    public void merge(User user);

    /**
     *
     * @param userId
     * @param value
     * @return
     */
    public boolean changeEnabled(Long userId, boolean value);

    /**
     *
     * @param mailAd
     * @return
     * @throws ParseException
     */
    public boolean getAllUserMailData(String mailAd) throws ParseException;

    /**
     *
     * @param userId
     * @return
     */
    public User findById(Long userId);

    /**
     *
     * @param field
     * @param value
     * @return
     */
    public User findByCustomField(String field, String value);

    /**
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    public boolean checkRoleUsedOrNot(Long roleId) throws Exception;

    /**
     *
     * @param userName
     * @return
     */
    public User getUserByUserName(String userName);
}

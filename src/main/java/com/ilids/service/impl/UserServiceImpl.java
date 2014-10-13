package com.ilids.service.impl;

import com.ilids.IRepository.UserRepository;
import com.ilids.IService.RoleService;
import com.ilids.IService.UserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.domain.Role;
import com.ilids.domain.User;
import java.text.ParseException;

/**
 *
 * @author cirakas
 */
@Component
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsersExceptAdmin() {
	return userRepository.getAllUsersExceptAdmin();
    }

    @Override
    public boolean addRoleToUser(Long roleId, Long userId) {
	User user = findById(userId);
	Role role = roleService.findById(roleId);
	if (role != null && user != null) {
	    user.addRole(role);
	    persist(user);
	} else {
	    return false;
	}
	return true;
    }

    @Override
    public boolean takeRoleFromUser(Long roleId, Long userId) {
	User user = findById(userId);
	Role role = roleService.findById(roleId);
	if (role != null && user != null) {
	    user.removeRole(role);
	    persist(user);
	} else {
	    return false;
	}
	return true;
    }

    @Override
    public boolean addNewUserToDatabase(User user) throws Exception {
	Long roleId = Long.valueOf(user.getRoleId());
	Role role = roleService.findById(roleId);
	Role userRole = roleService.findByName("ROLE_ADMIN");
	user.setRole(role);
	user.addRole(userRole);
	encryptPassword(user);
	persist(user);
	return true;
    }

    @Override
    public boolean updateNewUserToDatabase(User user) throws Exception {
	Long roleId = Long.valueOf(user.getRoleId());
	Role role = roleService.findById(roleId);
	Role userRole = roleService.findByName("ROLE_ADMIN");
	user.addRole(userRole);
	user.setRole(role);
	encryptPassword(user);
	//user.encryptPassword();
	merge(user);
	return true;
    }

    @Override
    public void encryptPassword(User user) {
	user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
    }

    @Override
    public void removeUserFromDatabase(Long userId) throws Exception {
	userRepository.delete(findById(userId));
    }

    @Override
    public void persist(User user) {
	userRepository.persist(user);
    }

    @Override
    public void merge(User user) {
	userRepository.merge(user);
    }

    @Override
    public boolean changeEnabled(Long userId, boolean value) {
	User user = findById(userId);
	user.setEnabled(value);
	persist(user);
	return true;
    }

    @Override
    public boolean getAllUserMailData(String mailAd) throws ParseException {
	List<Object[]> mailIdData = userRepository.getAllUserMailData(mailAd);
	boolean result = false;
	int mailSize = mailIdData.size();
	if (!mailIdData.isEmpty()) {
	    result = true;
	}
	return result;
    }

    @Override
    public User findById(Long userId) {
	User user = userRepository.findById(userId);
	return user;
    }

    @Override
    public User findByCustomField(String field, String value) {
	return userRepository.findByCustomField(field, value);
    }

    @Override
    public boolean checkRoleUsedOrNot(Long roleId) throws Exception {
	return userRepository.checkRoleUsed(roleId);
    }

    @Override
    public User getUserByUserName(String userName) {
	return userRepository.getUserByUserName(userName);
    }

}

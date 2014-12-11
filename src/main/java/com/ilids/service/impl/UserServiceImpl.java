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
        List<User> allUserExceptAdmin = userRepository.getAllUsersExceptAdmin();
        userRepository.close();
	return allUserExceptAdmin;
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
        userRepository.close();
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
        userRepository.close();
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
	//persist(user);
        userRepository.merge(user);
        userRepository.close();
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
        userRepository.close();
	return true;
    }

    @Override
    public void encryptPassword(User user) {
	user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
        userRepository.close();
    }

    @Override
    public User removeUserFromDatabase(Long userId) throws Exception {
	 User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException();
        }
        userRepository.delete(user);
        userRepository.close();
        return user;
    }

    @Override
    public void persist(User user) {
	userRepository.persist(user);
    }

    @Override
    public void merge(User user) {
	userRepository.merge(user);
        userRepository.close();
    }

    @Override
    public boolean changeEnabled(Long userId, boolean value) {
	User user = findById(userId);
	user.setEnabled(value);
	persist(user);
        userRepository.close();
	return true;
    }

    @Override
    public boolean getAllUserMailData(String mailAd,long id) throws ParseException {
	List<Object[]> mailIdData = userRepository.getAllUserMailData(mailAd,id);
        boolean result = true;
        if (!mailIdData.isEmpty()) {
            result = false;
        }
        userRepository.close();
	return result;
    }

    @Override
    public User findById(Long userId) {
	User user = userRepository.findById(userId);
        userRepository.close();
	return user;
    }

    @Override
    public User findByCustomField(String field, String value) {
        User customField = userRepository.findByCustomField(field, value);
        userRepository.close();
	return customField;
    }

    @Override
    public boolean checkRoleUsedOrNot(Long roleId) throws Exception {
        boolean roleUsedOrNot = userRepository.checkRoleUsed(roleId);
        userRepository.close();
	return roleUsedOrNot;
    }

    @Override
    public User getUserByUserName(String userName) {
        User userByUserName = userRepository.getUserByUserName(userName);
        userRepository.close();
	return userByUserName;
    }

}

package com.ilids.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.dao.UserRepository;
import com.ilids.domain.Role;
import com.ilids.domain.User;

@Component
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsersExceptAdmin() {
        return userRepository.getAllUsersExceptAdmin();
    }

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

    public boolean addNewUserToDatabase(User user) {
	Long roleId=Long.valueOf(user.getRoleId());
	Role role=roleService.findById(roleId);
        Role userRole = roleService.findByName("ROLE_ADMIN");
	user.setRole(role);
        user.addRole(userRole);
        encryptPassword(user);
        //user.encryptPassword();
        persist(user);
        return true;
    }
 public boolean updateNewUserToDatabase(User user) {
	Long roleId=Long.valueOf(user.getRoleId());
	Role role=roleService.findById(roleId);
        Role userRole = roleService.findByName("ROLE_ADMIN");
        user.addRole(userRole);
	user.setRole(role);
        encryptPassword(user);
        //user.encryptPassword();
        merge(user);
        return true;
    }

    private void encryptPassword(User user) {
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
    }

    public void removeUserFromDatabase(Long userId) {
        userRepository.delete(findById(userId));
    }

    public void persist(User user) {
        userRepository.persist(user);
    }

     public void merge(User user) {
        userRepository.merge(user);
    }
    
    public boolean changeEnabled(Long userId, boolean value) {
        User user = findById(userId);
        user.setEnabled(value);
        persist(user);
        return true;
    }

    public User findById(Long userId) {
        User user = userRepository.findById(userId);
        return user;
    }

    public User findByCustomField(String field, String value) {
        return userRepository.findByCustomField(field, value);
    }

    public boolean checkRoleUsedOrNot(Long roleId){
	 return userRepository.checkRoleUsed(roleId);
    }
    
    
    
}

package com.ilids.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.dao.UserRepository;
import com.ilids.domain.Role;
import com.ilids.domain.User;
import java.text.ParseException;
import javax.annotation.Resource;
import org.springframework.security.core.session.SessionRegistryImpl;

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

    public boolean addNewUserToDatabase(User user)throws Exception {
	Long roleId=Long.valueOf(user.getRoleId());
	Role role=roleService.findById(roleId);
        Role userRole = roleService.findByName("ROLE_ADMIN");
	user.setRole(role);
        user.addRole(userRole);
        encryptPassword(user);
        persist(user);
        return true;
    }
 public boolean updateNewUserToDatabase(User user)throws Exception {
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

    public void removeUserFromDatabase(Long userId)throws Exception {
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
    
     public boolean getAllUserMailData(String mailAd) throws ParseException {
       List<Object[]> mailIdData=userRepository.getAllUserMailData(mailAd); 
       boolean result=false;
       int mailSize=mailIdData.size();
        if(!mailIdData.isEmpty()){
            result=true;
        }
        return result;
    }   

    public User findById(Long userId) {
        User user = userRepository.findById(userId);
        return user;
    }

    public User findByCustomField(String field, String value) {
        return userRepository.findByCustomField(field, value);
    }

    public boolean checkRoleUsedOrNot(Long roleId)throws Exception{
	 return userRepository.checkRoleUsed(roleId);
    }
    
    public User getUserByUserName(String userName){
       return userRepository.getUserByUserName(userName);
    }
    
}

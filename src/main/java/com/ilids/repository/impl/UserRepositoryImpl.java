package com.ilids.repository.impl;

import com.ilids.IRepository.UserRepository;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ilids.domain.User;

/**
 *
 * @author Jeena
 */
@Component
public class UserRepositoryImpl extends GenericRepositoryImpl<User> implements UserRepository{

    /**
     *
     */
    public UserRepositoryImpl() {
        super(User.class);
    }

    /**
     *
     * @return
     */
    public List<User> getAllUsersExceptAdmin() {
        return super.runCustomQuery(entityManager.createQuery("SELECT c FROM User c WHERE c.username != 'admin'", User.class));
    }

    /**
     *
     * @param mailAd
     * @return
     */
    public List<Object[]> getAllUserMailData(String mailAd,long id) {
       // Object[] getMailAdd = null;
        //try{
        String selectMailQuery = "select * from user where email= '"+mailAd+"' and id!='"+id+"'";
        return (List<Object[]>) entityManager.createNativeQuery(selectMailQuery).getResultList();
    }
    
    /**
     *
     * @param roleId
     * @return
     */
    public boolean checkRoleUsed(Long roleId){
	String countQuery="SELECT COUNT(u.id) FROM User u where u.role="+roleId;
	Long count=null;
	boolean result=true;
	count=(Long) entityManager.createQuery(countQuery).getSingleResult();
	if(count==null || count==0){
	    result=false;
	}
	return result;
    }

    /**
     *
     * @param userName
     * @return
     */
    public User getUserByUserName(String userName){
        User user;
      try{
          System.out.println("Inside method");
            user=super.findByCustomField("username", userName);
      }catch(Exception e){
          user=null;
      }
      
      return user;
     }
    
    /**
     *
     * @param id
     * @return
     */
    public User findById(Long id){
        return super.findById(id);
    }
}

package com.ilids.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ilids.domain.User;

@Component
public class UserRepository extends AbstractGenericDao<User> {

    public UserRepository() {
        super(User.class);
    }

    public List<User> getAllUsersExceptAdmin() {
        return super.runCustomQuery(entityManager.createQuery("SELECT c FROM User c WHERE c.username != 'admin'", User.class));
    }
    
     public List<Object[]> getAllUserMailData(String mailAd) {
       // Object[] getMailAdd = null;
        //try{
        String selectMailQuery = "select * from user where email= '"+mailAd+"'";
        return (List<Object[]>) entityManager.createNativeQuery(selectMailQuery).getResultList();
    }
    
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

}

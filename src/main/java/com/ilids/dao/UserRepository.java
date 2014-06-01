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

}

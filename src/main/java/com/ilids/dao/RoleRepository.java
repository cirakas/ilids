package com.ilids.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ilids.domain.Role;

@Component
public class RoleRepository extends AbstractGenericDao<Role> {

    public RoleRepository() {
        super(Role.class);
    }
    
    public List<Role> getAllUsersExceptRestrictedOnes() {
        return super.runCustomQuery(entityManager.createQuery("SELECT c FROM Role c WHERE c.name != 'ROLE_ADMIN' AND c.name != 'ROLE_USER'", Role.class));
    }
}

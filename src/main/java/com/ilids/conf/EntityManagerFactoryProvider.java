/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.conf;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author cirakas
 */
public final class EntityManagerFactoryProvider {
   
    static  EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ilids");
        public static EntityManagerFactory getEntityManagerFactory(){         
        return entityManagerFactory;
    }
    
    
}

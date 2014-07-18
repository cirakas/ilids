package com.ilids.dao;

import org.springframework.stereotype.Component;

import com.ilids.domain.Data;
import java.util.List;

@Component
public class DataRepository extends AbstractGenericDao<Data> {

    public DataRepository() {
        super(Data.class);
    }
    

    public List<Data> getAllAlertData(double mdv) {
        return (List<Data>)entityManager.createQuery("SELECT u FROM Data u where data > "+mdv+" and (address_map=12 or address_map=14 or address_map=16)").getResultList();
    }
//    public List<Data> getAllData() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

}


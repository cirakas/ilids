package com.ilids.dao;

import java.util.List;

import javax.persistence.Query;

public interface GenericDao<T> {

    List<T> getAll();

    List<T> runCustomQuery(Query query);

    T findById(Number id);

    T findByCustomField(String key, String value);

    void persist(T t);

    void delete(T t);

    T merge(T t);
    
    int executeNativeQuery(String value);

}

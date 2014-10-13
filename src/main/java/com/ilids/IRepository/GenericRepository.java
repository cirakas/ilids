package com.ilids.IRepository;

import java.util.List;

import javax.persistence.Query;

/**
 *
 * @author cirakas
 * @param <T>
 */
public interface GenericRepository<T> {

    /**
     *
     * @return
     */
    List<T> getAll();

    /**
     *
     * @param query
     * @return
     */
    List<T> runCustomQuery(Query query);

    /**
     *
     * @param id
     * @return
     */
    T findById(Number id);

    /**
     *
     * @param key
     * @param value
     * @return
     */
    T findByCustomField(String key, String value);

    /**
     *
     * @param t
     */
    void persist(T t);

    /**
     *
     * @param t
     */
    void delete(T t);

    /**
     *
     * @param t
     * @return
     */
    T merge(T t);
    
    /**
     *
     * @param value
     * @return
     */
    int executeNativeQuery(String value);

}

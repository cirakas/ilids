package com.ilids.repository.impl;

import com.ilids.IRepository.GenericRepository;
import com.ilids.conf.EntityManagerFactoryProvider;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author cirakas
 * @param <T>
 */
public abstract class GenericRepositoryImpl<T> implements GenericRepository<T> {

    /**
     *
     */
    protected EntityManager entityManager;
    private Class<T> type;

    /**
     *
     * @param type
     */
    public GenericRepositoryImpl(Class<T> type) {
        entityManager = EntityManagerFactoryProvider.getEntityManagerFactory().createEntityManager();
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAll() {
        return entityManager.createQuery("SELECT u FROM " + type.getSimpleName() + " u").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> runCustomQuery(Query query) {
        return query.getResultList();
    }

    @Override
    public T findById(Number id) {
        return entityManager.find(type, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findByCustomField(String key, String value) {
       String queryString = "SELECT u FROM " + type.getSimpleName() + " u WHERE u." + key + " = '" + value + "'";
         Query query = entityManager.createQuery(queryString);
        return (T) query.getSingleResult();
    }

    @Override
    public void persist(T t) {
        entityManager.persist(t);
    }

    @Override
    public void delete(T t) {
        entityManager.remove(t);
    }

    @Override
    public T merge(T t) {
        return entityManager.merge(t);
    }
    
    @Override
    public int executeNativeQuery(String queryString){
        Query query = entityManager.createNativeQuery(queryString);
        return query.executeUpdate();
    }
    public void close(){
       // entityManager.close();
    }

}

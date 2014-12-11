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
        entityManager.getTransaction().begin();
        try{
        entityManager.persist(t);
        entityManager.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T t) {
        entityManager.getTransaction().begin();
        try{
        entityManager.remove(t);
        entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public T merge(T t) {
         T ts=null;
       entityManager.getTransaction().begin();
        try{
        ts= entityManager.merge(t);
        entityManager.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return ts;
    }
    
    @Override
    public int executeNativeQuery(String queryString){
        int result=0;
        entityManager.getTransaction().begin();
        try{
        Query query = entityManager.createNativeQuery(queryString);
        result=query.executeUpdate();
        entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        return result;
    }
    public void close(){
       // entityManager.close();
    }

}

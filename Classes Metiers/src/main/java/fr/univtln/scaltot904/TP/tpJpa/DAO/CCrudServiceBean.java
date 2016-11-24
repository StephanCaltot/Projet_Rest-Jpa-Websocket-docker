package fr.univtln.scaltot904.TP.tpJpa.DAO;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by scaltot904 on 26/02/16.
 */



public class CCrudServiceBean<T> implements ICrudService<T> {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("testpostgresqllocal");
    EntityManager em = emf.createEntityManager();

    public  T create(T t) {

        EntityTransaction transac = em.getTransaction();
        transac.begin();

        this.em.merge(t);
        this.em.flush();
        transac.commit();
        return t;
    }

    @SuppressWarnings("unchecked")
    public  T find(Class type,Object id) {
        return (T) this.em.find(type, id);
    }

    public void delete(Class type,Object id) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();

        Object ref = this.em.getReference(type, id);
        this.em.remove(ref);
        transac.commit();
    }

    public  T update(T t) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        t = (T)this.em.merge(t);
        transac.commit();
        return t;
    }

    public List findWithNamedQuery(String namedQueryName){
        return this.em.createNamedQuery(namedQueryName).getResultList();
    }

    public List findWithNamedQuery(String namedQueryName, Map parameters){
        return findWithNamedQuery(namedQueryName, parameters, 0);
    }

    public List findWithNamedQuery(String queryName, int resultLimit) {
        return this.em.createNamedQuery(queryName).
                setMaxResults(resultLimit).
                getResultList();
    }

    public List findByNativeQuery(String sql, Class type) {
        return this.em.createNativeQuery(sql, type).getResultList();
    }

    public List findWithNamedQuery(String namedQueryName, Map parameters,int resultLimit){
        Set <Map.Entry> rawParameters = parameters.entrySet();
        Query query = this.em.createNamedQuery(namedQueryName);
        if(resultLimit > 0)
            query.setMaxResults(resultLimit);
        for (Map.Entry entry : rawParameters) {
            query.setParameter((String) entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }
}


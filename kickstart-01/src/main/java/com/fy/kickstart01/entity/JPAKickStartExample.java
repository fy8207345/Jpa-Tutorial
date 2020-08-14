package com.fy.kickstart01.entity;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Slf4j
public class JPAKickStartExample {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        MyObject myObject = new MyObject();
        myObject.setStr("one");
        entityManager.persist(myObject);

        myObject = new MyObject();
        myObject.setStr("two");
        entityManager.persist(myObject);

        entityManager.getTransaction().commit();

        findById(entityManager);
        queryWithJpql(entityManager);
        typedQueryWithJPQL(entityManager);
        queryNative(entityManager);
        criteriaQuery(entityManager);
    }

    private static void criteriaQuery(EntityManager entityManager){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = criteriaBuilder.createQuery();
        CriteriaQuery<Object> select = query.select(query.from(MyObject.class));
        TypedQuery<Object> typedQuery = entityManager.createQuery(select);
        log.info("criteriaQuery :{}", typedQuery.getResultList());
    }

    private static void findById(EntityManager entityManager){
        MyObject o = entityManager.find(MyObject.class, 2L);
        log.info("----\nfinding object by id : {}", o);
    }

    private static void queryWithJpql(EntityManager entityManager){
        Query query = entityManager.createQuery("select t from MyObject t");
        List resultList1 = query.getResultList();
        log.info("----\nQuerying using JPQL : {}", resultList1);
    }

    private static void typedQueryWithJPQL(EntityManager entityManager){
        TypedQuery<MyObject> query = entityManager.createQuery("select t from MyObject t", MyObject.class);
        List<MyObject> list = query.getResultList();
        log.info("----\nQuerying using typedQueryWithJPQL : {}", list);
    }

    private static void queryNative(EntityManager entityManager){
        Query query = entityManager.createNativeQuery("select * from MyObject");
        List list = query.getResultList();
        for(Object o : list){
            log.info("queryNative object : {}", o);
        }
    }
}

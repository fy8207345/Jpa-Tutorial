package com.fy.persistabletypes;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Slf4j
public class ExampleDefaultEnumPersistence {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testPersistenceUnit");

    public static void main(String[] args) {
        showGeneratedTables();
        persistEntities();
        loadEntities();
    }

    private static void showGeneratedTables(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Util.nativeQuery(entityManager, "show tables");
        Util.nativeQuery(entityManager, "show columns from MyEntity");
        entityManager.close();
    }

    private static void persistEntities(){
        MyEntity entity = new MyEntity(1, MyEnum.ConstC);
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        log.info("MyEntity persisted: {}", entity);
    }

    private static void loadEntities() {
        EntityManager em = entityManagerFactory.createEntityManager();
        Util.nativeQuery(em, "select * from MyEntity");
        MyEntity myEntity = em.find(MyEntity.class, 1);
        System.out.printf("Entity loaded by entity manager: %s%n", myEntity);
        em.close();
    }
}

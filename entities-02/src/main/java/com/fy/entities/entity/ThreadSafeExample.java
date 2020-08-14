package com.fy.entities.entity;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class ThreadSafeExample {

    public static void main(String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testPersistenceUnit");
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<?>> futures = new ArrayList<Future<?>>();
        Future<?> future = executorService.submit(() -> {
            return runTask(entityManagerFactory, 1, "test 1");
        });
        Future<?> future1 = executorService.submit(() -> {
            return runTask(entityManagerFactory, 2, "test 2");
        });
        futures.add(future);
        futures.add(future1);
        try {
            for(Future<?> future2 : futures){
                future.get();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        nativeQuery(entityManagerFactory, "select * from MyEntity");
        executorService.shutdown();
        entityManagerFactory.close();
    }

    private static void nativeQuery(EntityManagerFactory entityManagerFactory, String s) {
        log.info("--------\n" + s);
        Query query = entityManagerFactory.createEntityManager().createNativeQuery(s);
        List list = query.getResultList();
        for (Object o : list) {
            log.info(Arrays.toString((Object[]) o));
        }
    }

    private static String runTask(EntityManagerFactory entityManagerFactory, int id, String str){
        log.info("persisting id: {}, str: {}", id, str);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(createNewMyEntity(id,str));
        entityManager.getTransaction().commit();
        return "done executing id: " + id;
    }

    private static MyEntity createNewMyEntity(int id, String str) {
        MyEntity entity = new MyEntity();
        entity.setId(id);
        entity.setMyString(str);
        return entity;
    }
}

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
public class NotThreadSafeExample {
    public static void main(String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testPersistenceUnit");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        List<Future<?>> futures = new ArrayList<Future<?>>();
        Future<?> future = executorService.submit(() -> {
            return runTask(entityManager, 1, "test 1");
        });
        Future<?> future1 = executorService.submit(() -> {
            return runTask(entityManager, 2, "test 2");
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

        nativeQuery(entityManager, "select * from MyEntity");
        executorService.shutdown();
        entityManager.close();
        entityManagerFactory.close();
    }

    private static void nativeQuery(EntityManager entityManager, String s) {
        log.info("--------\n" + s);
        Query query = entityManager.createNativeQuery(s);
        List list = query.getResultList();
        for (Object o : list) {
            log.info(Arrays.toString((Object[]) o));
        }
    }

    private static String runTask(EntityManager entityManager, int id, String str){
        log.info("persisting id: {}, str: {}", id, str);
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

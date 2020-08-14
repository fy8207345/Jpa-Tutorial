package com.fy.persistabletypes;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Slf4j
public class Util {

    public static void nativeQuery(EntityManager entityManager, String s){
        Query query = entityManager.createNativeQuery(s);
        List list = query.getResultList();
        for(Object o : list){
            log.info("query: {} - {}", s, o);
        }
    }
}

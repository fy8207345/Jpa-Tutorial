package com.fy.kickstart01.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ToString
@Data
@Entity
public class MyObject {

    @Id
    @GeneratedValue
    private long objId;

    private String str;
}

package com.fy.entities.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class MyEntity {

    @Id
    @Column(name = "example_id")
    private Integer id;

    @Column(name = "example_string")
    private String myString;

    @Column(name = "example_integer")
    private int myInteger;

    @Transient
    private String notPersistedField;
}

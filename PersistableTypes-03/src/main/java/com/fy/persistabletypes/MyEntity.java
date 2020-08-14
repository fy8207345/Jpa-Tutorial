package com.fy.persistabletypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyEntity {

    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private MyEnum myEnum;
}

package com.epam.esm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Tag domain.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    @Id
    private Long id;

    @Column
    private String name;
}
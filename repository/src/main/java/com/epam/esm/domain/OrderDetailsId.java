package com.epam.esm.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailsId implements Serializable {
    private Long order;

    private Long certificate;
}

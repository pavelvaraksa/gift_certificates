package com.epam.esm.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailsId implements Serializable {
    Long order;

    Long certificate;
}

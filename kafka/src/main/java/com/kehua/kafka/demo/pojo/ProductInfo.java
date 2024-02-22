package com.kehua.kafka.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class ProductInfo implements Serializable {

    private Long id;

    private String name;
}

package com.id.entity;

import java.io.Serializable;

public interface BaseEntity<K extends Serializable> {

    void setId(K key);

    K getId();
}

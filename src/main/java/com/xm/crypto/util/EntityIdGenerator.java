package com.xm.crypto.util;

import java.util.UUID;

public class EntityIdGenerator {

    private static EntityIdGenerator onlyInstance;

    public static EntityIdGenerator getInstance() {
        if (onlyInstance == null) {
            onlyInstance = new EntityIdGenerator();
        }
        return onlyInstance;
    }

    private EntityIdGenerator(){
    }

    public synchronized String nextId() {
        return UUID.randomUUID().toString();
    }
}

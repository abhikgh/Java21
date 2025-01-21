package com.src.hackerRank;

import java.util.Objects;

class Singleton {

    private static volatile Singleton singletonEx = null;

    // prevent Object creation by Constructor
    private Singleton() {
        // do nothing
    }

    // get Object instance by Double-check locking
    public static Singleton getSingleInstance() {
        if (Objects.isNull(singletonEx)) {
            synchronized (Singleton.class) {
                if (Objects.isNull(singletonEx)) {
                    singletonEx = new Singleton();
                }
            }
        }
        return singletonEx;
    }

    // prevent Object creation by Cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}
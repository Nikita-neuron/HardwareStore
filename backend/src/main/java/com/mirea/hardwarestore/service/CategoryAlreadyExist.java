package com.mirea.hardwarestore.service;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

public class CategoryAlreadyExist extends Exception {
    public CategoryAlreadyExist(String msg) {
        super(msg);
    }
}

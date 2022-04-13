package com.assigment.suretime.generics;

import org.apache.commons.lang3.NotImplementedException;

public interface MongoModel<T> {

    public String getId();

    public void update(T model);
}

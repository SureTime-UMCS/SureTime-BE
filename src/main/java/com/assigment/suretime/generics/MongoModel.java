package com.assigment.suretime.generics;

public interface MongoModel<T> {

    public String getId();

    /**
     * This methods updates T.x field only if model.x != null.
     */
    public void updateNotNullFields(T model);

}

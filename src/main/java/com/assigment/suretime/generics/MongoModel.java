package com.assigment.suretime.generics;

public interface MongoModel{

    public String getId();

    /**
     * This methods updates T.x field only if model.x != null.
     */
    public void updateNotNullFields(Object o);

}

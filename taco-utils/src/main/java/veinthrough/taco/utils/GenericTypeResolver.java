package veinthrough.taco.utils;

import java.lang.reflect.ParameterizedType;

public class GenericTypeResolver<T> {
    protected Class<T> getGenericType() {
        return (Class<T>)((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0].getClass();
    }
}

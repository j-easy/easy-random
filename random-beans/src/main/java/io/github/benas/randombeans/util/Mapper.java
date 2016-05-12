package io.github.benas.randombeans.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by dkopel on 5/11/16.
 */
public enum Mapper {
    INSTANCE;

    static private ObjectMapper objectMapper = new ObjectMapper();

    ObjectMapper objectMapper() {
        return objectMapper;
    }

    <T> T convertValue(Object obj, Class<T> type) {
        return objectMapper.convertValue(obj, type);
    }

    <T> T convertValue(Object obj, TypeReference<T> typeReference) {
        return objectMapper.convertValue(obj, typeReference);
    }

    <T> T convertValue(Object obj, JavaType javaType) {
        return objectMapper.convertValue(obj, javaType);
    }




}

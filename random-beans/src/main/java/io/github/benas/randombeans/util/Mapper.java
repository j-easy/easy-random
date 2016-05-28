package io.github.benas.randombeans.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by dkopel on 5/11/16.
 */
public enum Mapper {
    INSTANCE;

    static private final ObjectMapper objectMapper = new ObjectMapper();

    <T> T convertValue(Object obj, Class<T> type) {
        return objectMapper.convertValue(obj, type);
    }


}

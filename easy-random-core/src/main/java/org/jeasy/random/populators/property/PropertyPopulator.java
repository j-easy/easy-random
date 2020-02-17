package org.jeasy.random.populators.property;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public interface PropertyPopulator {

  void poulateField(final Object object, final Field field, final Object value)
      throws IllegalAccessException, IntrospectionException, InvocationTargetException;
}

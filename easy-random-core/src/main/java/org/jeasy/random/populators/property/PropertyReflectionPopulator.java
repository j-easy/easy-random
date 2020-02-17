package org.jeasy.random.populators.property;

import java.lang.reflect.Field;

public class PropertyReflectionPopulator implements PropertyPopulator {

  @Override
  public void poulateField(Object object, Field field, Object value)
      throws IllegalAccessException {
    boolean access = field.isAccessible();
    field.setAccessible(true);
    field.set(object, value);
    field.setAccessible(access);
  }
}

package org.jeasy.random.populators.property;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class PropertySetterPopulator implements PropertyPopulator {

  @Override
  public void poulateField(Object object, Field field, Object value)
      throws IllegalAccessException, IntrospectionException, InvocationTargetException {
    PropertyDescriptor pd;
    pd = new PropertyDescriptor(field.getName(), object.getClass());
    pd.getWriteMethod().invoke(object, value);
  }
}

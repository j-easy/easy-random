package io.github.benas.jpopulator.randomizers.internal;

import io.github.benas.jpopulator.api.EnumRandomizer;
import io.github.benas.jpopulator.util.ConstantsUtil;

public class DefaultEnumRandomizer implements EnumRandomizer {

    @Override
    public <E extends Enum<E>> E getRandomEnumValue(Class<E> enumClass) {
        E[] enumConstants = enumClass.getEnumConstants();
        return enumConstants[ConstantsUtil.RANDOM.nextInt(enumConstants.length)];
    }
}

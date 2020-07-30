/*******************************************************************************
 * Copyright (c) 2018 Nosto Solutions Ltd All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Nosto Solutions Ltd ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the agreement you entered into with
 * Nosto Solutions Ltd.
 ******************************************************************************/
package org.jeasy.random.beans;

public abstract class BoundedBaseClass<T extends BoundedBaseClass.SomeInterface> {
    private final T x;

    public BoundedBaseClass(T x) {
        this.x = x;
    }

    public T getX() {
        return x;
    }

    public interface SomeInterface { }

    public static class IntWrapper implements SomeInterface {
        private final int value;

        public IntWrapper(int value) {
            this.value = value;
        }
    }
}

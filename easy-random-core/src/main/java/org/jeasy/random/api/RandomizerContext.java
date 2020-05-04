/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.jeasy.random.api;

import java.util.List;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

/**
 * A context object for a {@link Randomizer}.
 * This interface provides information about the randomization context.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public interface RandomizerContext {

    /**
     * Return the target type (parameter of {@link EasyRandom#nextObject(Class)}).
     * @return target type
     */
    Class<?> getTargetType();

    /**
     * Return the root object being randomized (instance of {@link RandomizerContext#getTargetType()}.
     * @return root object being randomized
     */
    Object getRootObject();

    /**
     * Return the currently randomized object in the object graph.
     * @return currently randomized object
     */
    Object getCurrentObject();

    /**
     * Return the full path to the current field being randomized (starting from the first field in the root type).
     * @return full path to the current field being randomized
     */
    String getCurrentField();

    /**
     * Get the current level in the hierarchy of the object graph.
     * @return current level in the hierarchy of the object graph.
     */
    int getCurrentRandomizationDepth();

    /**
     * Return the currently used parameters by the enclosing {@link EasyRandom}.
     * @return currently used parameters
     */
    EasyRandomParameters getParameters();

    /**
     * Get a random element from the list.
     *
     * @param list the input list
     * @param <T>  the type of elements in the list
     * @return a random element from the list or null if the list is empty
     */
    <T> T randomElementOf(List<T> list);
}
